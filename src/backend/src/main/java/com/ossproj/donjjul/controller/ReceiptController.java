// controller/ReceiptController.java
package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.*;
import com.ossproj.donjjul.service.ProposalService;
import com.ossproj.donjjul.service.ReviewService;
import com.ossproj.donjjul.service.ReceiptService;
import com.ossproj.donjjul.util.OcrClient;
import com.ossproj.donjjul.repository.StoreRepository;
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
    private final StoreRepository storeRepo;
    private final ProposalService proposalSvc;
    private final ReviewService reviewSvc;

    @PostMapping("/process")
    public ResponseEntity<?> processReceipt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("rating") int rating,
            @RequestParam("content") String content
    ) throws IOException {
        // 1) OCR
        OcrResponseDto ocr = ocrClient.requestOcr(file);
        if (!ocr.isSuccess()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "OCR 실패"));
        }

        // 2) 영수증 검증
        ReceiptValidationResult vr = receiptService.verifyReceipt(
                ocr.getBusinessNumber(), ocr.getPayDate()
        );
        if (!vr.isValid()) {
            return ResponseEntity.ok(Map.of(
                    "business_number", ocr.getBusinessNumber(),
                    "pay_date",        ocr.getPayDate(),
                    "valid",          false,
                    "reason",         vr.getReason()
            ));
        }

        // 3) 매장 존재하면 후기, 없으면 제보
        return storeRepo.findByBusinessNumber(ocr.getBusinessNumber())
                .map(store -> {
                    ReviewCreateRequest req = new ReviewCreateRequest();
                    req.setStoreId(store.getId());
                    req.setRating(rating);
                    req.setContent(content);
                    ReviewResponse rr = reviewSvc.createReview(req);
                    return ResponseEntity.ok(Map.of(
                            "type",   "review",
                            "review", rr
                    ));
                })
                .orElseGet(() -> {
                    ProposalResponseDto pr = proposalSvc.createProposal(ocr);
                    return ResponseEntity.ok(Map.of(
                            "type",     "proposal",
                            "proposal", pr
                    ));
                });
    }

    // 기존 /verify 엔드포인트도 그대로 유지
}
