package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.Certificate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CertificateResponse {
    private String userName;
    private String storeName;
    private LocalDateTime paymentDate;

    public static CertificateResponse from(Certificate cert) {
        return CertificateResponse.builder()
                .storeName(cert.getReview().getStore().getName())
                .paymentDate(cert.getReview().getPaymentDate())  // 또는 적절한 필드명
                .build();
    }
}
