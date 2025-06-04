package com.ossproj.donjjul.repository;

import com.ossproj.donjjul.domain.ProposalVote;
import com.ossproj.donjjul.domain.StoreProposal;
import com.ossproj.donjjul.domain.User;            // 이 라인 추가
import com.ossproj.donjjul.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;                              // 이 라인 추가

public interface ProposalVoteRepository extends JpaRepository<ProposalVote, Long> {

    // 중복 투표 체크
    boolean existsByUserAndProposal(User user, StoreProposal proposal);

    // 제보별 찬성·반대 전체 투표수 조회
    long countByProposalAndVoteType(StoreProposal proposal, VoteType voteType);

    // 제보별 전체 투표 리스트 조회
    List<ProposalVote> findAllByProposalId(Long proposalId);
}
