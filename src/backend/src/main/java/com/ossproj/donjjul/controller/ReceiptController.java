// src/backend/src/main/java/com/ossproj/donjjul/controller/ReceiptController.java
package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.*;
import com.ossproj.donjjul.service.ProposalService;
import com.ossproj.donjjul.service.ReviewService;
import com.ossproj.donjjul.service.ReceiptService;
import com.ossproj.donjjul.util.OcrClient;
import com.ossproj.donjjul.domain.Store;
import com.ossproj.donjjul.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final OcrClient ocrClient;
    private final ReceiptService receiptService;
    private final StoreRepository storeRepo;
    private final ReviewService reviewSvc;
    private final ProposalService proposalSvc;

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processReceipt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("rating") int rating,
            @RequestParam("content") String content
    ) throws IOException {
        // 1) OCR 호출
        OcrResponseDto ocr = ocrClient.requestOcr(file);
        if (!ocr.isSuccess()) {
            return ResponseEntity.badRequest().body(Map.of("message", "OCR 실패"));
        }

        LocalDate payDate = ocr.getPayDate();
        String businessNumber = ocr.getBusinessNumber();
        String payDateStr = payDate != null ? payDate.toString() : null;

        // 2) payDate null 체크 → 안내
        if (payDate == null) {
            return ResponseEntity.ok(Map.of(
                "valid", false,
                "status", "invalid",
                "reason", "잘못된 날짜 형식",
                "business_number", businessNumber,
                "pay_date", payDateStr
            ));
        }

        // 3) 영수증 검증 → status로 분기
        ReceiptValidationResult vr = receiptService.verifyReceipt(businessNumber, payDate);

        Map<String, Object> body = new HashMap<>();
        body.put("business_number", businessNumber);
        body.put("pay_date", payDateStr);
        body.put("valid", vr.isValid());
        body.put("status", vr.getStatus());
        body.put("reason", vr.getReason());

        // (필요하다면 추가 정보 같이 전달)
        return ResponseEntity.ok(body);
    }
}
