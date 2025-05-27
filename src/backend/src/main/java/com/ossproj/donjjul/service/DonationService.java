package com.ossproj.donjjul.service;

import com.ossproj.donjjul.domain.Donation;
import com.ossproj.donjjul.domain.DonationTarget;
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.dto.CharacterResponse;
import com.ossproj.donjjul.repository.DonationRepository;
import com.ossproj.donjjul.repository.DonationTargetRepository;
import com.ossproj.donjjul.repository.UserRepository;
import com.ossproj.donjjul.config.CharacterConfig;
import com.ossproj.donjjul.enums.CharacterStage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final DonationTargetRepository targetRepository;

    public DonationService(DonationRepository donationRepository,
                           UserRepository userRepository,
                           DonationTargetRepository targetRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.targetRepository = targetRepository;
    }

    @Transactional
    public CharacterResponse donate(Long userId, Long targetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getCharacterStage() != CharacterStage.ADULT) {
            throw new IllegalStateException("성체가 아닌 상태에서는 기부할 수 없습니다.");
        }

        DonationTarget target = targetRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Donation target not found"));

        Donation donation = new Donation(user, target, CharacterConfig.DONATION_AMOUNT);
        donationRepository.save(donation);

        user.setDonationPoints(0);
        user.setCharacterStage(CharacterStage.BABY);
        userRepository.save(user);

        return new CharacterResponse(
                user.getDonationPoints(),
                user.getCharacterStage()
        );
    }
}
