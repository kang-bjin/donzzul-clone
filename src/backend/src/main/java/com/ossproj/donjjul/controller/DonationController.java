package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.dto.CharacterResponse;
import com.ossproj.donjjul.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping("/{userId}/{targetId}")
    public ResponseEntity<CharacterResponse> donate(
            @PathVariable Long userId,
            @PathVariable Long targetId) {
        CharacterResponse response = donationService.donate(userId, targetId);
        return ResponseEntity.ok(response);
    }
}
