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
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/proposals")
@RequiredArgsConstructor
public class StoreProposalController {

    private final StoreProposalService proposalService;
    private final UserService userService;

    @PostMapping
    public StoreProposalResponse create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody StoreProposalRequest req) {
        Long userId = userDetails != null ? Long.parseLong(userDetails.getUsername()) : 1L; // username에 id 저장된 경우
        StoreProposal proposal = proposalService.createProposal(userId, req);
        // ✅ 포인트 적립
        User user = userService.findById(userId);     // 유저 정보 조회
        user.addDonationPoints(100);                  // 100포인트 추가 (User 엔티티에 메서드가 없다면 직접 setter나 += 연산)
        userService.save(user);                       // 변경 사항 저장
        
        return StoreProposalResponse.from(proposal);
    }

    @GetMapping("/user/{userId}")
    public List<StoreProposalResponse> getProposalsByUserId(@PathVariable Long userId) {
        return proposalService.getProposalsByUserId(userId);
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
