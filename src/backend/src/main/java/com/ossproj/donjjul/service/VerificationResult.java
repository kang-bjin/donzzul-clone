package com.ossproj.donjjul.service;

/**
 * 영수증 인증 결과를 담는 VO
 */
public record VerificationResult(
        boolean valid,
        String reason
) {}
