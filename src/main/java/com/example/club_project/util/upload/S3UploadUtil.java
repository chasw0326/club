package com.example.club_project.util.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.club_project.config.AwsConfigure;
import com.example.club_project.controller.common.AttachedFile;
import com.example.club_project.exception.custom.InvalidArgsException;
import com.example.club_project.exception.custom.UploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * UploadUtil 구현체 (S3 사용)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadUtil implements UploadUtil {

    private final AmazonS3Client amazonS3Client;
    private final AwsConfigure awsConfigure;

    @Override
    public String upload(AttachedFile uploadFile, String uploadPath) {
        byte[] bytes = uploadFile.getBytes();
        String filename = uploadFile.getOriginalFileName();
        String contentType = uploadFile.getContentType();

        return upload(bytes, uploadPath, filename, contentType);
    }

    private String upload(byte[] bytes, String uploadPath, String filename, String contentType) {
        String key = uploadPath + "/" + UUID.randomUUID() + filename;
        return upload(new ByteArrayInputStream(bytes), bytes.length, key, contentType);
    }

    private String upload(InputStream in, long length, String key, String contentType) {

        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(length);
        objectMetadata.setContentType(contentType);

        PutObjectRequest putObjectRequest = new PutObjectRequest(awsConfigure.getBucket(), key, in, objectMetadata);
        return putS3(putObjectRequest);
    }

    private String putS3(PutObjectRequest request) {
        amazonS3Client.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(awsConfigure.getBucket(), request.getKey()).toString();
    }
}
