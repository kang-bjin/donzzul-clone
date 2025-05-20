// src/backend/src/main/java/com/ossproj/donjjul/controller/ReceiptController.java
package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.OcrResponseDto;
import com.ossproj.donjjul.dto.ReceiptValidationResult;
import com.ossproj.donjjul.service.ReceiptService;
import com.ossproj.donjjul.util.OcrClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final OcrClient ocrClient;
    private final ReceiptService receiptService;

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyReceipt(@RequestParam("file") MultipartFile file) throws IOException {
        // 1) OCR 요청
        OcrResponseDto ocrResult = ocrClient.requestOcr(file);
        if (!ocrResult.isSuccess()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "OCR 실패"));
        }

        // 2) 비즈니스 검증
        ReceiptValidationResult result = receiptService.verifyReceipt(
                ocrResult.getBusinessNumber(),
                ocrResult.getPayDate()
        );

        // 3) 응답 바디 조립 (항상 200)
        Map<String, Object> body = new HashMap<>();
        body.put("business_number", ocrResult.getBusinessNumber());
        body.put("pay_date", ocrResult.getPayDate());
        body.put("valid", result.isValid());
        if (!result.isValid()) {
            body.put("reason", result.getReason());
        }

        return ResponseEntity.ok(body);
    }
}
