package com.nareumserver.domain;

import com.nareumserver.type.Category;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    private Long id;
    private String originalName;
    private String extension;
    private String url;
    private String s3Key;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
