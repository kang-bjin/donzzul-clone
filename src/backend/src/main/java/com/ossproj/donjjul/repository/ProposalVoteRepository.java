package com.ossproj.donjjul.repository;

import com.ossproj.donjjul.domain.ProposalVote;
import com.ossproj.donjjul.domain.StoreProposal;
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalVoteRepository extends JpaRepository<ProposalVote, Long> {

    // ✅ 중복 투표 체크용
    boolean existsByUserAndProposal(User user, StoreProposal proposal);

    // (선택) 제보별 투표 수 조회
    long countByProposalAndVoteType(StoreProposal proposal, VoteType voteType);
}
