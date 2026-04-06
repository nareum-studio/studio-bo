package com.nareumserver.controller;

import com.nareumserver.common.dto.ResponseDTO;
import com.nareumserver.dto.ImageDTO.BasicResponse;
import com.nareumserver.service.ImageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/list")
    public ResponseEntity<?> getImageList(@RequestParam String category) {
        List<BasicResponse> imageList = imageService.getImageList(category);

        return ResponseEntity.ok(new ResponseDTO<>("이미지 조회 성공", imageList));
    }


}
