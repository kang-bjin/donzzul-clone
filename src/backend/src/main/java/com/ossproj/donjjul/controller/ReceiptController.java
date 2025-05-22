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
            Map<String,Object> body = new HashMap<>();
            body.put("message", "OCR 실패");
            return ResponseEntity.badRequest().body(body);
        }

        // 2) payDate null 체크
        LocalDate payDate = ocr.getPayDate();
        if (payDate == null) {
            Map<String,Object> body = new HashMap<>();
            body.put("message", "OCR에서 결제일을 추출하지 못했습니다");
            return ResponseEntity.badRequest().body(body);
        }

        String businessNumber = ocr.getBusinessNumber();
        String payDateStr = payDate.toString();

        // 3) 영수증 검증
        ReceiptValidationResult vr = receiptService.verifyReceipt(businessNumber, payDate);
        if (!vr.isValid()) {
            Map<String,Object> body = new HashMap<>();
            body.put("business_number", businessNumber);
            body.put("pay_date", payDateStr);
            body.put("valid", false);
            body.put("reason", vr.getReason());
            return ResponseEntity.ok(body);
        }

        // 4) 매장 존재 여부에 따른 분기
        Optional<Store> optStore = storeRepo.findByBusinessNumber(businessNumber);
        if (optStore.isPresent()) {
            ReviewCreateRequest req = new ReviewCreateRequest();
            req.setStoreId(optStore.get().getId());
            req.setRating(rating);
            req.setContent(content);
            ReviewResponse rr = reviewSvc.createReview(req);

            Map<String,Object> body = new HashMap<>();
            body.put("type", "review");
            body.put("review", rr);
            return ResponseEntity.ok(body);
        } else {
            ProposalResponseDto pr = proposalSvc.createProposal(ocr);
            Map<String,Object> body = new HashMap<>();
            body.put("type", "proposal");
            body.put("proposal", pr);
            return ResponseEntity.ok(body);
        }
    }

    // (기존 /verify 엔드포인트는 그대로 유지)
}
