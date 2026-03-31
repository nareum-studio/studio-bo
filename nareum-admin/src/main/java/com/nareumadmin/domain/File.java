package com.nareumadmin.domain;

import com.nareumadmin.type.Category;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

    private Long id;
    private String originalName;
    private String extension;
    private String url;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
