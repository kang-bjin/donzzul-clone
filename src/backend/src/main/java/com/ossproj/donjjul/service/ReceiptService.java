package com.ossproj.donjjul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import com.ossproj.donjjul.repository.StoreRepository;

/**
 * 영수증의 유효성을 검증하는 서비스
 */
@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final StoreRepository storeRepository;

    /**
     * 영수증 인증: 결제일(payDateStr)이 3일 이내인지 검사하고,
     * 사업자번호(businessNumber)가 DB에 존재하는지 확인
     *
     * @param businessNumber 사업자번호
     * @param payDateStr ISO 포맷 날짜 문자열 (yyyy-MM-dd)
     * @return 인증 성공 여부
     */
    public boolean verifyReceipt(String businessNumber, String payDateStr) {
        LocalDate payDate;
        try {
            payDate = LocalDate.parse(payDateStr);
        } catch (Exception e) {
            return false;
        }

        // 인증 기한: 결제일로부터 3일 이내
        if (payDate.isBefore(LocalDate.now().minusDays(3))) {
            return false;
        }

        // DB에 사업자번호 존재 여부 검사
        return storeRepository.existsByBusinessNumber(businessNumber);
    }
}