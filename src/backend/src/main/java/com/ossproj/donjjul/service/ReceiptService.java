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
     * 1) payDate null → 날짜 파싱 실패
     * 2) 결제일이 3일 초과
     * 3) DB에 사업자번호 미존재
     */
    public ReceiptValidationResult verifyReceipt(String businessNumber, LocalDate payDate) {
        String payDateStr = payDate != null ? payDate.toString() : null;

        // 1) 날짜가 아예 없으면 파싱 실패로 처리
        if (payDate == null) {
            return new ReceiptValidationResult(
                    false,
                    businessNumber,
                    payDateStr,
                    "잘못된 날짜 형식"
            );
        }

        // 2) 유효 기간(3일) 초과 체크
        if (payDate.isBefore(LocalDate.now().minusDays(3))) {
            return new ReceiptValidationResult(
                    false,
                    businessNumber,
                    payDateStr,
                    "유효 기간(3일) 초과"
            );
        }

        // 3) DB에 등록된 사업자번호인지 체크
        if (!storeRepository.existsByBusinessNumber(businessNumber)) {
            return new ReceiptValidationResult(
                    false,
                    businessNumber,
                    payDateStr,
                    "등록되지 않은 사업자번호"
            );
        }

        // 모두 통과
        return new ReceiptValidationResult(
                true,
                businessNumber,
                payDateStr,
                null
        );
    }
}
