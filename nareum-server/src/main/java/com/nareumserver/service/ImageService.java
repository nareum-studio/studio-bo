package com.nareumserver.service;

import com.nareumserver.dto.ImageDTO.BasicResponse;
import com.nareumserver.mapper.ImageMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;

    public List<BasicResponse> getImageList(String category) {
        return imageMapper.getImageList(category).stream().map(BasicResponse::of).toList();
    }


}
