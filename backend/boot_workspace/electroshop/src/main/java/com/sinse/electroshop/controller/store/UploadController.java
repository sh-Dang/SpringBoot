package com.sinse.electroshop.controller.store;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    @Value("${app.upload.dir}")
    private String uploadDir;

    private Path path;

    @PostMapping("/upload")
    @PostConstruct
    public void init(){
        log.debug("업로드 될 파일의 경로는 {}", uploadDir);
        path = Path.of(uploadDir);

    }

    @PostMapping("/file/upload")
    public ResponseEntity<String> upload(MultipartFile file) throws Exception{
        log.debug("파일명은 " + file.getOriginalFilename());
        String oriName = file.getOriginalFilename();
        String ext = file.getOriginalFilename().substring(oriName.lastIndexOf(".")+1, oriName.length());
        String fileName = UUID.randomUUID().toString()+"."+ext;
        log.debug(fileName);

        Path target = path.resolve(fileName); //기존 Path 객체에 파일명까지 적용

        file.transferTo(target);
        return ResponseEntity.ok("업로드 성공");
    }

    @PostMapping("/files/upload")
    public ResponseEntity<String> uploadFiles(MultipartFile[] files) throws Exception{
        for(MultipartFile file : files){
            String oriName = file.getOriginalFilename();
            String ext = file.getOriginalFilename().substring(oriName.lastIndexOf(".")+1, oriName.length());
            String fileName = UUID.randomUUID().toString()+"."+ext;
            log.debug(fileName);
            file.transferTo(path.resolve(fileName));
        }

        return ResponseEntity.ok("파일들 업로드 성동일");
    }
}
