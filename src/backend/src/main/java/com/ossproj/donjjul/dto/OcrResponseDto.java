package com.ossproj.donjjul.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OcrResponseDto {
    @JsonProperty("business_number")
    private String businessNumber;

    @JsonProperty("pay_date")
    private String payDate;

    private boolean success;

    private String message;
}
