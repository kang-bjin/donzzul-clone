// src/backend/src/main/java/com/ossproj/donjjul/service/ProposalService.java
package com.ossproj.donjjul.service;

import com.ossproj.donjjul.domain.StoreProposal;
import com.ossproj.donjjul.dto.OcrResponseDto;
import com.ossproj.donjjul.dto.ProposalResponseDto;
import com.ossproj.donjjul.enums.ProposalStatus;
import com.ossproj.donjjul.repository.StoreProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private final StoreProposalRepository proposalRepo;

    /**
     * OCR 결과로 신규 매장 제안 저장 후 DTO 반환
     */
    @Transactional
    public ProposalResponseDto createProposal(OcrResponseDto ocr) {
        StoreProposal proposal = StoreProposal.builder()
                .businessNumber(ocr.getBusinessNumber())
                .status(ProposalStatus.VOTING)
                .build();

        proposal = proposalRepo.save(proposal);

        return new ProposalResponseDto(proposal);
    }
}
