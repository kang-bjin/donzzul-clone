package com.ossproj.donjjul.util;

import com.ossproj.donjjul.dto.OcrResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class OcrClient {

    private static final String OCR_URL = "http://ocr:5000/ocr";

    public OcrResponseDto requestOcr(MultipartFile imageFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(
                imageFile.getInputStream(), imageFile.getOriginalFilename()
        ));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<OcrResponseDto> response = restTemplate.postForEntity(
                OCR_URL, requestEntity, OcrResponseDto.class
        );

        return response.getBody();
    }
}
