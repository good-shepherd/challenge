package com.midasit.challenge.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class UploadUtils {

    @Value("${app.uploadPath}")
    private String filePath;

    public List<String> saveFiles(List<MultipartFile> files) {
        List<String> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            UUID uid = UUID.randomUUID();
            try {
                byte[] bytes = file.getBytes();
                // path will be: "E:/midasitimages/uid_originalfFileName"
                Path path = Paths.get(filePath.concat(uid.toString()).concat("_").concat(file.getOriginalFilename()));
                uploadedFiles.add(path.toString());
                log.info("Uploading - " + path.toString());
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uploadedFiles;
    }

    public void deleteFiles(List<String> images) {
        images.forEach(o -> {
            try {
                Files.delete(Paths.get(o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
