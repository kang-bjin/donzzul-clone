// DonationService.java
package com.ossproj.donjjul.service;

import com.ossproj.donjjul.dto.CharacterResponse;
import com.ossproj.donjjul.domain.Donation;
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.repository.DonationRepository;
import org.springframework.stereotype.Service;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public CharacterResponse donate(User user, int amount) {
        // 실제 기부 DB 기록
        Donation donation = new Donation(user, amount);
        donationRepository.save(donation);

        // 사용자 상태 반환
        return new CharacterResponse(
                user.getDonationPoints(),
                user.getCharacterStage()
        );
    }
}
