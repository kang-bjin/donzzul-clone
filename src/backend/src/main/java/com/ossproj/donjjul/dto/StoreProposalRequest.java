// StoreProposalRequest.java
package com.ossproj.donjjul.dto;

import lombok.Getter;
import lombok.Data;
import lombok.Setter;

@Getter
@Data
@Setter
public class StoreProposalRequest {
    private String businessNumber;
    private String storeAddress;
    private String storeName;
    private String category;
    private String reason;
    private String review;
}
