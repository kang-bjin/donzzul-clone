// src/backend/src/main/java/com/ossproj/donjjul/service/ReceiptService.java
package com.ossproj.donjjul.service;

import com.ossproj.donjjul.dto.ReceiptValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import com.ossproj.donjjul.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final StoreRepository storeRepository;

    /**
     * 영수증 인증:
     * 결제일이 30일 초과: 인증 불가 (status: expired)
     * 결제일이 30일 이내 + 사업자번호 O: 기존 리뷰로 (status: registered)
     * 결제일이 30일 이내 + 사업자번호 X: 제보로 (status: unregistered)
     */
    public ReceiptValidationResult verifyReceipt(String businessNumber, LocalDate payDate) {
        String payDateStr = payDate != null ? payDate.toString() : null;

        // 1) 날짜 파싱 실패
        if (payDate == null) {
            return new ReceiptValidationResult(
                false,
                businessNumber,
                payDateStr,
                "잘못된 날짜 형식",
                "invalid_date"
            );
        }

        // 2) 결제일이 30일 초과
        if (payDate.isBefore(LocalDate.now().minusDays(50))) {
            return new ReceiptValidationResult(
                false,
                businessNumber,
                payDateStr,
                "유효 기간(30일) 초과되어 인증이 불가합니다",
                "expired"
            );
        }

        // 3) 결제일이 유효한데 사업자번호 DB에 있으면 기존 리뷰로
        if (storeRepository.existsByBusinessNumber(businessNumber)) {
            return new ReceiptValidationResult(
                true,
                businessNumber,
                payDateStr,
                null,
                "registered"
            );
        }

        // 4) 결제일이 유효한데 사업자번호가 없으면 제보 플로우로
        return new ReceiptValidationResult(
            false,   // 리뷰 플로우 X (즉, 제보만 가능)
            businessNumber,
            payDateStr,
            "등록되지 않은 사업자번호",
            "unregistered"
            
        );
    }
}