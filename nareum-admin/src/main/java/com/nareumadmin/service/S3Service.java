package com.nareumadmin.service;

import com.nareumadmin.dto.ImageDTO.UploadResult;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.cloudfront.domain}")
    private String cloudfrontDomain;

    public UploadResult upload(MultipartFile file, String category) {
        String ext = file.getOriginalFilename()
            .substring(file.getOriginalFilename().lastIndexOf("."));
        String originalFileName = file.getOriginalFilename();
        String fileName = category + "/" + UUID.randomUUID() + ext;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .contentType(file.getContentType())
                .cacheControl("public, max-age=31536000, immutable")
                .build();

            s3Client.putObject(request,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }

        String url = "https://" + cloudfrontDomain + "/" + fileName;

        return UploadResult.builder()
            .url(url)
            .s3Key(fileName)
            .originalFileName(originalFileName)
            .build();
    }

    public void deleteFile(String s3Key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
            .bucket(bucket)
            .key(s3Key)
            .build();

        s3Client.deleteObject(request);
    }
}
