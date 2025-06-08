package com.ossproj.donjjul.util;

import com.ossproj.donjjul.dto.OcrResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OcrClient {

    @Value("${ocr.url}")
    private String ocrUrl;

    private final RestTemplate restTemplate;

    public OcrResponseDto requestOcr(MultipartFile imageFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(
                imageFile.getInputStream(), imageFile.getOriginalFilename()
        ));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<OcrResponseDto> response = restTemplate
                .postForEntity(ocrUrl, requestEntity, OcrResponseDto.class);

        System.out.println("OCR 응답 본문: " + response.getBody());
        System.out.println("OCR 응답 상태: " + response.getStatusCode());

        return response.getBody();
    }
}
