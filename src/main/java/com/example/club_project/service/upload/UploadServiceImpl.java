package com.example.club_project.service.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public String uploadFile(MultipartFile uploadFile, String uploadPath){
        return null;
    }
}