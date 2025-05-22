package com.ossproj.donjjul.service;

import com.ossproj.donjjul.domain.ProposalVote;
import com.ossproj.donjjul.domain.StoreProposal;
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.dto.ProposalVoteResponse;
import com.ossproj.donjjul.enums.VoteType;
import com.ossproj.donjjul.repository.ProposalVoteRepository;
import com.ossproj.donjjul.repository.StoreProposalRepository;
import com.ossproj.donjjul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProposalVoteService {

    private final ProposalVoteRepository voteRepo;
    private final StoreProposalRepository proposalRepo;
    private final UserRepository userRepo;

    @Transactional
    public ProposalVote vote(Long userId, Long proposalId, VoteType voteType) {
        User user = userRepo.findById(userId).orElseThrow();
        StoreProposal proposal = proposalRepo.findById(proposalId).orElseThrow();

        // 중복 투표 방지
        if (voteRepo.existsByUserAndProposal(user, proposal)) {
            throw new IllegalArgumentException("이미 투표했습니다.");
        }

        ProposalVote vote = ProposalVote.builder()
                .user(user)
                .proposal(proposal)
                .voteType(voteType)
                .createdAt(LocalDateTime.now())
                .build();

        return voteRepo.save(vote);
    }

    @Transactional(readOnly = true)
    public List<ProposalVoteResponse> listByProposal(Long proposalId) {
        return voteRepo.findAllByProposalId(proposalId).stream()
                .map(ProposalVoteResponse::from)
                .collect(Collectors.toList());
    }
}
