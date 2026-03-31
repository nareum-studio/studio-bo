package com.nareumadmin.controller;

import com.nareumadmin.common.dto.ResponseDTO;
import com.nareumadmin.dto.FileDTO.UploadResponse;
import com.nareumadmin.service.FileService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("files") List<MultipartFile> files,
        @RequestParam String category) {

        List<UploadResponse> responseList = fileService.uploadFiles(files, category);

        return ResponseEntity.ok(new ResponseDTO<>("파일 업로드 성공", responseList));
    }
}
