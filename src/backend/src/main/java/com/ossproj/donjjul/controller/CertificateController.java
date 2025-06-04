package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.CertificateResponse;
import com.ossproj.donjjul.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService certService;

    @PostMapping("/{reviewId}")
    public ResponseEntity<Void> issue(@PathVariable Long reviewId) {
        Long userId = 1L;  // JWT 안 쓰는 상태니까 하드코딩
        certService.issueCertificate(userId, reviewId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CertificateResponse>> list() {
        Long userId = 1L;
        return ResponseEntity.ok(certService.getCertificates(userId));
    }
}
