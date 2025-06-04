package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.StoreProposal;
import lombok.Getter;

@Getter
public class ProposalResponseDto {
    private Long id;
    private String businessNumber;
    private String storeName;
    private String status;

    public ProposalResponseDto(StoreProposal p) {
        this.id = p.getId();
        this.businessNumber = p.getBusinessNumber();
        this.storeName = p.getStoreName();
        this.status = p.getStatus().name();
    }
}
