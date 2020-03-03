package com.tvj.internaltool.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile multipartFile);

    String convertImageToBase64(String fileName);

}
