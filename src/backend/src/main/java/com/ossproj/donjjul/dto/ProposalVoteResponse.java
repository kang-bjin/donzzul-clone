package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.ProposalVote;
import com.ossproj.donjjul.enums.VoteType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProposalVoteResponse {
    private Long id;
    private Long userId;
    private Long proposalId;
    private VoteType voteType;
    private LocalDateTime createdAt;

    public static ProposalVoteResponse from(ProposalVote vote) {
        return ProposalVoteResponse.builder()
                .id(vote.getId())
                .userId(vote.getUser().getId())
                .proposalId(vote.getProposal().getId())
                .voteType(vote.getVoteType())
                .createdAt(vote.getCreatedAt())
                .build();
    }
}
