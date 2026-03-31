package com.nareumadmin.dto;

import com.nareumadmin.domain.File;
import com.nareumadmin.type.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FileDTO {

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

        public static UploadResponse of(File file) {
            return UploadResponse.builder()
                .id(file.getId())
                .category(file.getCategory())
                .originalName(file.getOriginalName())
                .url(file.getUrl())
                .extension(file.getExtension())
                .build();
        }
    }
}
