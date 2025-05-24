package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.Certificate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CertificateResponse {
    private Long certificateId;
    private String storeName;
    private LocalDate issuedDate;

    public static CertificateResponse from(Certificate c) {
        return new CertificateResponse(
                c.getId(),
                c.getReview().getStore().getName(),
                c.getIssuedDate()
        );
    }
}
