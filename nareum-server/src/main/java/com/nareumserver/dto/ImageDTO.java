package com.nareumserver.dto;

import com.nareumserver.domain.Image;
import com.nareumserver.type.Category;
import java.time.LocalDateTime;
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
    public static class BasicResponse {

        private Long id;
        private Category category;
        private String originalName;
        private String url;
        private String extension;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static BasicResponse of(Image image) {
            return BasicResponse.builder()
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
}
