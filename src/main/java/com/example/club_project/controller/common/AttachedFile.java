package com.example.club_project.controller.common;

import com.example.club_project.exception.custom.UploadException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
@RequiredArgsConstructor
public class AttachedFile {

    private final String originalFileName;

    private final String contentType;

    private final byte[] bytes;

    public static AttachedFile toAttachedFile(MultipartFile uploadFile) {
        try {
            if (isImageFile(uploadFile)) {
                return new AttachedFile(uploadFile.getOriginalFilename(), uploadFile.getContentType(), uploadFile.getBytes());
            }

            throw new UploadException("이미지 파일 형식만 업로드 할 수 있습니다.");

        } catch (IOException ignored) {
            throw new UploadException("일시적인 에러가 발생했습니다. 반복적으로 에러 발생 시 관리자에게 문의해주세요.");
        }
    }

    private static boolean isImageFile(MultipartFile uploadFile) {
        if (ObjectUtils.isNotEmpty(uploadFile)
                && uploadFile.getSize() > 0
                && StringUtils.isNotEmpty(uploadFile.getOriginalFilename())) {

            String contentType = uploadFile.getContentType();
            return isNotEmpty(contentType) && contentType.toLowerCase().startsWith("image");
        }

        return false;
    }
}
