package com.nareumadmin.service;

import com.nareumadmin.domain.Image;
import com.nareumadmin.dto.ImageDTO.UploadResponse;
import com.nareumadmin.dto.ImageDTO.UploadResult;
import com.nareumadmin.mapper.ImageMapper;
import com.nareumadmin.type.Category;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageService {

    private final ImageMapper imageMapper;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public List<UploadResponse> getImageList(String category) {
        return imageMapper.getImageList(category).stream().map(UploadResponse::of).toList();
    }

    public List<UploadResponse> uploadFiles(List<MultipartFile> files, String category) {
        log.info("{}개의 이미지 업로드", files.size());

        return files.stream()
            .map(file -> {
                UploadResult uploadResult = s3Service.upload(file, category);
                String originalFileName = uploadResult.getOriginalFileName();
                String extension = getFileExtension(originalFileName);

                Image uploadImage = Image.builder()
                    .originalName(originalFileName)
                    .url(uploadResult.getUrl())
                    .s3Key(uploadResult.getS3Key())
                    .category(Category.valueOf(category))
                    .extension(extension)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(null)
                    .build();

                imageMapper.upload(uploadImage);

                return UploadResponse.of(uploadImage);
            }).toList();
    }

    public List<UploadResponse> updateFiles(List<MultipartFile> files, String category,
        List<Long> deleteFileIds) {

        if (deleteFileIds != null && !deleteFileIds.isEmpty()) {
            deleteFiles(deleteFileIds);
        }

        if (files != null && !files.isEmpty()) {
            return uploadFiles(files, category);
        }

        return List.of();
    }

    public void deleteFiles(List<Long> fileIds) {
        log.info("{}개의 이미지 삭제", fileIds.size());

        List<Image> images = imageMapper.findByIds(fileIds);

        // S3에서 삭제
        images.forEach(image -> s3Service.deleteFile(image.getS3Key()));

        // DB에서 삭제
        imageMapper.deleteByIds(fileIds);
    }


    //  "."의 존재 유무만 판단
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
