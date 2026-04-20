package com.nareumadmin.dto;

import com.nareumadmin.domain.Image;
import com.nareumadmin.type.Category;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ImageDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UploadResult {

        private String url;
        private String s3Key;
        private String originalFileName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UploadResponse {

        private Long id;
        private Category category;
        private String originalName;
        private String url;
        private String extension;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static UploadResponse of(Image image) {
            return UploadResponse.builder()
                .id(image.getId())
                .category(image.getCategory())
                .originalName(image.getOriginalName())
                .url(image.getUrl())
                .extension(image.getExtension())
                .createdAt(image.getCreatedAt())
                .updatedAt(image.getUpdatedAt())
                .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {

        private List<Long> deleteImages;
    }
}
