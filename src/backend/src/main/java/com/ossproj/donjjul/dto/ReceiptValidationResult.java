package com.ossproj.donjjul.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Data;

@Data
@Getter
@AllArgsConstructor
public class ReceiptValidationResult {
    private final boolean valid;
    private final String businessNumber;
    private final String payDate;
    private final String reason; // valid==false 일 때 사용자에게 보여줄 메시지
    private String status;             // 상태값: expired/registered/unregistered
}
