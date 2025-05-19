package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.OcrResponseDto;
import com.ossproj.donjjul.service.ReceiptService;
import com.ossproj.donjjul.util.OcrClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
            return ResponseEntity.badRequest().body(Map.of("message", "OCR 실패"));
        }

        boolean isValid = receiptService.verifyReceipt(
                ocrResult.getBusinessNumber(),
                ocrResult.getPayDate()
        );

        return ResponseEntity.ok(Map.of(
                "valid", isValid,
                "business_number", ocrResult.getBusinessNumber(),
                "pay_date", ocrResult.getPayDate()
        ));
    }
}