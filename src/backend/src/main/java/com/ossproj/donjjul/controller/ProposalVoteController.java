package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.ProposalVoteRequest;
import com.ossproj.donjjul.dto.ProposalVoteResponse;
import com.ossproj.donjjul.service.ProposalVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposals/{proposalId}/votes")
@RequiredArgsConstructor
public class ProposalVoteController {

    private final ProposalVoteService voteService;

    @PostMapping
    public ProposalVoteResponse vote(
            @PathVariable Long proposalId,
            @RequestBody ProposalVoteRequest request) {
        // 인증 제거: 임시로 모든 투표를 userId=1로 처리
        Long userId = 1L; // TODO: 추후 인증 로직 추가
        var vote = voteService.vote(userId, proposalId, request.getVoteType());
        return ProposalVoteResponse.from(vote);
    }

    @GetMapping
    public List<ProposalVoteResponse> listVotes(@PathVariable Long proposalId) {
        return voteService.listByProposal(proposalId);
    }
}