// src/backend/src/main/java/com/ossproj/donjjul/service/ReceiptValidationResult.java
package com.ossproj.donjjul.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReceiptValidationResult {
    private final boolean valid;
    private final String reason; // valid == false 일 때 사유
}
