package com.ossproj.donjjul.dto;

import java.time.LocalDate;

public record VerifyReceiptResponse(
        boolean valid,
        String businessNumber,
        LocalDate payDate,
        String reason
) {}
