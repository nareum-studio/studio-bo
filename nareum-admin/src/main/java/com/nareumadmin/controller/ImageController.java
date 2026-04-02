package com.nareumadmin.controller;

import com.nareumadmin.common.dto.ResponseDTO;
import com.nareumadmin.dto.ImageDTO.UpdateRequest;
import com.nareumadmin.dto.ImageDTO.UploadResponse;
import com.nareumadmin.service.ImageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    // 업로드만
    // @PostMapping("/upload")
    // public ResponseEntity<?> upload(@RequestParam("files") List<MultipartFile> files,
    //     @RequestParam String category) {
    //
    //     List<UploadResponse> responseList = fileService.uploadFiles(files, category);
    //
    //     return ResponseEntity.ok(new ResponseDTO<>("파일 업로드 성공", responseList));
    // }

    @GetMapping("/list")
    public ResponseEntity<?> getImageList(@RequestParam String category) {
        List<UploadResponse> imageList = imageService.getImageList(category);

        return ResponseEntity.ok(new ResponseDTO<>("이미지 조회 성공", imageList));
    }

    // 업로드, 삭제 동시에 진행
    @PostMapping("/update")
    public ResponseEntity<?> updateFiles(
        @RequestPart(value = "newImages", required = false) List<MultipartFile> files,
        @RequestParam String category,
        @RequestPart(required = false) UpdateRequest deleteImages
    ) {
        List<Long> deleteFileIds = deleteImages != null ? deleteImages.getDeleteImages() : null;
        List<UploadResponse> responses = imageService.updateFiles(files, category, deleteFileIds);
        return ResponseEntity.ok(new ResponseDTO<>("이미지 수정 성공", responses));
    }
}
