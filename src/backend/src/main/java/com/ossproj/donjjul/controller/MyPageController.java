package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.ReviewResponse;
import com.ossproj.donjjul.service.ReviewService;
import com.ossproj.donjjul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my")
public class MyPageController {

    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getMyReviews() {
        Long userId = 1L; // JWT 인증 붙이기 전 임시
        List<ReviewResponse> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/nickname")
    public ResponseEntity<Map<String, String>> getMyNickname() {
        Long userId = 1L; // JWT 붙이기 전 임시값
        String nickname = userService.getNicknameByUserId(userId);
        Map<String, String> result = new HashMap<>();
        result.put("nickname", nickname);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/proposals")
    public ResponseEntity<List<ProposalResponse>> getMyProposals() {
        Long userId = 1L;       // ((CustomUserDetails)auth.getPrincipal()).getId();
        List<ProposalResponse> proposals = proposalService.getProposalsByUserId(userId);
        return ResponseEntity.ok(proposals);
    }
}
