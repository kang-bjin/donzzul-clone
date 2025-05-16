// StoreProposalResponse.java
package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.StoreProposal;
import com.ossproj.donjjul.enums.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StoreProposalResponse {
    private Long id;
    private String storeName;
    private String storeAddress;
    private String businessNumber;
    private String reason;
    private ProposalStatus status;
    private LocalDateTime createdAt;

    public static StoreProposalResponse from(StoreProposal proposal) {
        return new StoreProposalResponse(
                proposal.getId(),
                proposal.getStoreName(),
                proposal.getStoreAddress(),
                proposal.getBusinessNumber(),
                proposal.getReason(),
                proposal.getStatus(),
                proposal.getCreatedAt()
        );
    }
}
