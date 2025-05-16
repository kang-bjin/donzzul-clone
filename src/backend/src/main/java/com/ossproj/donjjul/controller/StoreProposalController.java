// StoreProposalController.java
package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.domain.StoreProposal;
import com.ossproj.donjjul.dto.StoreProposalRequest;
import com.ossproj.donjjul.dto.StoreProposalResponse;
import com.ossproj.donjjul.service.StoreProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/proposals")
@RequiredArgsConstructor
public class StoreProposalController {

    private final StoreProposalService proposalService;

    @PostMapping
    public StoreProposalResponse create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody StoreProposalRequest req) {
        Long userId = Long.parseLong(userDetails.getUsername()); // username에 id 저장된 경우
        StoreProposal proposal = proposalService.createProposal(userId, req);
        return StoreProposalResponse.from(proposal);
    }

    @GetMapping
    public List<StoreProposalResponse> getAll() {
        return proposalService.getAllProposals().stream()
                .map(StoreProposalResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StoreProposalResponse getById(@PathVariable Long id) {
        return StoreProposalResponse.from(proposalService.getProposalById(id));
    }

    @PutMapping("/{id}")
    public StoreProposalResponse update(@PathVariable Long id, @RequestBody StoreProposalRequest req) {
        return StoreProposalResponse.from(proposalService.updateProposal(id, req));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        proposalService.deleteProposal(id);
    }
}
