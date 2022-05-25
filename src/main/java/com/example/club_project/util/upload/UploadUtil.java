package com.example.club_project.util.upload;

import com.example.club_project.controller.common.AttachedFile;


public interface UploadUtil {

    /**
     * @param uploadFile 업로드 파일
     * @param uploadPath 저장할 폴더경로
     * @return saveName (이 Url를 DB에 저장)
     */
    String upload(AttachedFile uploadFile, String uploadPath);
}