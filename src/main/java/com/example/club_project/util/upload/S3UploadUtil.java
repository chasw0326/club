package com.example.club_project.util.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.club_project.config.AwsConfigure;
import com.example.club_project.exception.custom.InvalidArgsException;
import com.example.club_project.exception.custom.UnHandleException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
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
    public String upload(MultipartFile uploadFile, String uploadPath) {

        if (!isImageFile(uploadFile)) {
            throw new InvalidArgsException("You can only use the image file format");
        }

        try {
            // 파일로 변환할 수 없으면 에러
            File convertedFile = convert(uploadFile)
                    .orElseThrow(() -> new UnHandleException(
                            String.format("File convert fail with %s", uploadFile.getOriginalFilename()))
                    );

            return upload(convertedFile, uploadPath);

        } catch (IOException ignored) {
            throw new UnHandleException(String.format("ignored IOException occur with %s",uploadFile.getOriginalFilename()));
        }
    }

    private boolean isImageFile(MultipartFile uploadFile) {
        if (ObjectUtils.isNotEmpty(uploadFile)
            && uploadFile.getSize() > 0
            && StringUtils.isNotEmpty(uploadFile.getOriginalFilename())) {

            String contentType = uploadFile.getContentType();
            return isNotEmpty(contentType) && contentType.toLowerCase().startsWith("image");
        }

        return false;
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        String bucket = awsConfigure.getBucket();

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.debug("파일이 삭제되었습니다.");
        } else {
            log.debug("파일이 삭제되지 않았습니다.");
        }
    }
}