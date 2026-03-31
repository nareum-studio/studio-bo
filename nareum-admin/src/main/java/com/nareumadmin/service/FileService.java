package com.nareumadmin.service;

import com.nareumadmin.domain.File;
import com.nareumadmin.dto.FileDTO.UploadResponse;
import com.nareumadmin.mapper.FileMapper;
import com.nareumadmin.type.Category;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;
    private final S3Service s3Service;

    public List<UploadResponse> uploadFiles(List<MultipartFile> files, String category) {
        return files.stream()
            .map(file -> {
                URL url = s3Service.upload(file, category);
                String originalName = file.getOriginalFilename();
                String extension = getFileExtension(originalName);

                File uploadFile = File.builder()

                    .originalName(originalName)
                    .url(url.toString())
                    .category(Category.valueOf(category))
                    .extension(extension)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(null)
                    .build();

                fileMapper.upload(uploadFile);

                return UploadResponse.of(uploadFile);
            }).toList();
    }


    //  "."의 존재 유무만 판단
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    // public void deleteFile(String fileName) {
    //     amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    //     System.out.println(bucket);
    // }
}
