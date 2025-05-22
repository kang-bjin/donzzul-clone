// src/backend/src/main/java/com/ossproj/donjjul/dto/OcrResponseDto.java
package com.ossproj.donjjul.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class OcrResponseDto {
    private boolean success;
    private String businessNumber;
    private LocalDate payDate;

    // 추가
    private String storeName;
}
