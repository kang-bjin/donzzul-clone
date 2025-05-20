package com.ossproj.donjjul.util;

import org.springframework.core.io.InputStreamResource;
import java.io.InputStream;

/**
 * InputStreamResource를 MultipartFile로 전송하기 위해 사용하는 유틸 클래스
 */
public class MultipartInputStreamFileResource extends InputStreamResource {
    private final String filename;

    public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() {
        // 전송 시 파일 사이즈를 알 수 없어 -1로 설정 (S3 전송 등에서 사용)
        return -1;
    }
}