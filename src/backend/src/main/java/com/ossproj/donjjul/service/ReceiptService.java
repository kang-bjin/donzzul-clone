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
     * 1) 날짜 파싱 실패
     * 2) 결제일이 3일 초과
     * 3) DB에 사업자번호 미존재
     */
    public ReceiptValidationResult verifyReceipt(String businessNumber, String payDateStr) {
        LocalDate payDate;
        try {
            payDate = LocalDate.parse(payDateStr);
        } catch (Exception e) {
            return new ReceiptValidationResult(
                    false,
                    businessNumber,
                    payDateStr,
                    "잘못된 날짜 형식"
            );
        }

        if (payDate.isBefore(LocalDate.now().minusDays(3))) {
            return new ReceiptValidationResult(
                    false,
                    businessNumber,
                    payDateStr,
                    "유효 기간(3일) 초과"
            );
        }

        if (!storeRepository.existsByBusinessNumber(businessNumber)) {
            return new ReceiptValidationResult(
                    false,
                    businessNumber,
                    payDateStr,
                    "등록되지 않은 사업자번호"
            );
        }

        return new ReceiptValidationResult(
                true,
                businessNumber,
                payDateStr,
                null
        );
    }
}
