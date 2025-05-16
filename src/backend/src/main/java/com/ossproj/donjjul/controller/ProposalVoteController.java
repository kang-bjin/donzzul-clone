package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.ProposalVoteRequest;
import com.ossproj.donjjul.dto.ProposalVoteResponse;
import com.ossproj.donjjul.security.CustomUserDetails;
import com.ossproj.donjjul.service.ProposalVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proposals/{proposalId}/votes")
@RequiredArgsConstructor
public class ProposalVoteController {

    private final ProposalVoteService voteService;

    @PostMapping
    public ProposalVoteResponse vote(@AuthenticationPrincipal CustomUserDetails userDetails,
                                     @PathVariable Long proposalId,
                                     @RequestBody ProposalVoteRequest request) {
        var vote = voteService.vote(userDetails.getUser().getId(), proposalId, request.getVoteType());
        return ProposalVoteResponse.from(vote);
    }

    // (선택) DELETE, GET 등 나중에 추가 가능
}
