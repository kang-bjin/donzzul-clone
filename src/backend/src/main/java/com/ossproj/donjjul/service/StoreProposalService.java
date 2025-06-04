// StoreProposalService.java
package com.ossproj.donjjul.service;

import com.ossproj.donjjul.domain.StoreProposal;
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.dto.StoreProposalRequest;
import com.ossproj.donjjul.enums.ProposalStatus;
import com.ossproj.donjjul.repository.StoreProposalRepository;
import com.ossproj.donjjul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ossproj.donjjul.dto.StoreProposalResponse;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreProposalService {

    private final StoreProposalRepository proposalRepo;
    private final UserRepository userRepo;

    public StoreProposal createProposal(Long userId, StoreProposalRequest req) {
        User user = userRepo.findById(userId).orElseThrow();

        // 사업자번호 중복 제보 방지
        if (proposalRepo.existsByBusinessNumber(req.getBusinessNumber())) {
            throw new IllegalArgumentException("이미 제보된 사업자번호입니다.");
        }

        StoreProposal proposal = StoreProposal.builder()
                .user(user)
                .storeName(req.getStoreName())
                .storeAddress(req.getStoreAddress())
                .businessNumber(req.getBusinessNumber())
                .reason(req.getReason())
                .status(ProposalStatus.VOTING) // ✅ 기본값 VOTING
                .createdAt(LocalDateTime.now())
                .build();

        return proposalRepo.save(proposal);
    }


    public List<StoreProposal> getAllProposals() {
        return proposalRepo.findAll();
    }

    public StoreProposal getProposalById(Long id) {
        return proposalRepo.findById(id).orElseThrow();
    }

    public void deleteProposal(Long id) {
        proposalRepo.deleteById(id);
    }

    public StoreProposal updateProposal(Long id, StoreProposalRequest req) {
        StoreProposal proposal = proposalRepo.findById(id).orElseThrow();
        proposal.setStoreName(req.getStoreName());
        proposal.setStoreAddress(req.getStoreAddress());
        proposal.setBusinessNumber(req.getBusinessNumber());
        proposal.setReason(req.getReason());
        return proposalRepo.save(proposal);
    }

    public List<StoreProposalResponse> getProposalsByUserId(Long userId) {
    return proposalRepo.findByUserId(userId)
        .stream()
        .map(StoreProposalResponse::from)
        .collect(Collectors.toList());
}

}
