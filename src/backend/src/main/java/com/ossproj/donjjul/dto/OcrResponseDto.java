// src/backend/src/main/java/com/ossproj/donjjul/dto/OcrResponseDto.java
package com.ossproj.donjjul.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class OcrResponseDto {
    private boolean success;

    @JsonProperty("business_number")
    private String businessNumber;

    @JsonProperty("pay_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate payDate;
}
