package com.sinse.producservice.model.product;

import com.sinse.producservice.domain.ProductFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class UploadManager {
    @Value("${file.upload-dir}")
    private String uploadDir;

    // 여러 파일을 한 번에 저장하고, ProductFile 리스트를 반환하는 메소드
    public List<ProductFile> saveFile(List<MultipartFile> files, int productId) {
        List<ProductFile> productFileList = new ArrayList<>();

        try {
            // 루트 디렉토리 생성
            createDirectory(uploadDir);
            // 상품별 디렉토리 경로 생성
            Path savePath = createDirectory(uploadDir + "/p" + productId);

            for (MultipartFile file : files) {
                // 파일명 생성
                String originalName = file.getOriginalFilename();
                String extension = "";
                if (originalName != null && originalName.contains(".")) {
                    extension = originalName.substring(originalName.lastIndexOf("."));
                }
                String newFilename = UUID.randomUUID().toString() + extension;
                log.debug("새로운 파일명: {}", newFilename);

                // 파일 저장
                Path targetLocation = savePath.resolve(newFilename);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                // ProductFile 객체 생성 및 리스트 추가
                ProductFile productFile = new ProductFile();
                productFile.setFileName(newFilename);
                productFile.setOriginalName(originalName);
                productFile.setContentType(file.getContentType());
                productFile.setFilePath(targetLocation.toString());
                productFile.setFileSize(file.getSize());
                productFileList.add(productFile);
            }
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생", e);
            // 실제 프로덕션 코드에서는 사용자 정의 예외를 던지는 것이 좋습니다.
            throw new RuntimeException("이미지 파일 저장에 실패했습니다.", e);
        }
        return productFileList;
    }

    // 디렉토리 생성 메서드 정의
    public Path createDirectory(String path) throws IOException {
        Path dir = Paths.get(path);
        // toAbsolutePath()를 사용하여 절대 경로로 변환
        Path savePath = dir.toAbsolutePath().normalize();
        if (!Files.exists(savePath)) {
            Files.createDirectories(savePath); // createDirectories로 변경하여 부모 디렉토리까지 생성
            log.debug("{} 디렉토리 생성", savePath);
        }
        return savePath;
    }
}