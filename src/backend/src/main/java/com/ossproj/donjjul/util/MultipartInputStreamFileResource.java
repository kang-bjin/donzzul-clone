package com.ossproj.donjjul.util;

import org.springframework.core.io.InputStreamResource;
import java.io.InputStream;
import java.io.IOException;
/**
 * InputStreamResource를 MultipartFile로 전송하기 위해 사용하는 유틸 클래스
 */
public class MultipartInputStreamFileResource extends InputStreamResource {

    private final String filename;
    private final long contentLength;

    public MultipartInputStreamFileResource(InputStream inputStream, String filename, long contentLength) {
        super(inputStream);
        this.filename = filename;
        this.contentLength = contentLength;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return contentLength; // 정확한 길이를 반환해야 multipart boundary 작성이 가능함
    }

}