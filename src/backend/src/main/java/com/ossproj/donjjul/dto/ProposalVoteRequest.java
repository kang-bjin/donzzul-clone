package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.enums.VoteType;
import lombok.Getter;

@Getter
public class ProposalVoteRequest {
    private VoteType voteType;
}
