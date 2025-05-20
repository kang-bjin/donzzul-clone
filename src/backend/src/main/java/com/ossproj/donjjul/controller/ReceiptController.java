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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final OcrClient ocrClient;
    private final ReceiptService receiptService;

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyReceipt(@RequestParam("file") MultipartFile file) throws IOException {
        OcrResponseDto ocrResult = ocrClient.requestOcr(file);

        if (!ocrResult.isSuccess()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "OCR 실패"));
        }

        ReceiptValidationResult result = receiptService.verifyReceipt(
                ocrResult.getBusinessNumber(),
                ocrResult.getPayDate()
        );

        if (!result.isValid()) {
            // 유효하지 않은 경우 400 리턴하고 reason 포함
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "valid", false,
                            "reason", result.getReason(),
                            "business_number", ocrResult.getBusinessNumber(),
                            "pay_date", ocrResult.getPayDate()
                    ));
        }

        // 성공
        return ResponseEntity.ok(Map.of(
                "valid", true,
                "business_number", ocrResult.getBusinessNumber(),
                "pay_date", ocrResult.getPayDate()
        ));
    }
}
