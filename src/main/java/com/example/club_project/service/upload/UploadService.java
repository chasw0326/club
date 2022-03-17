package com.example.club_project.service.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    /**
     * @param uploadFile 업로드 파일
     * @param uploadPath 저장할 폴더경로
     * @return saveName (이 Url를 DB에 저장)
     */
    String uploadFile(MultipartFile uploadFile, String uploadPath);
}