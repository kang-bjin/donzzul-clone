package com.ossproj.donjjul.service;

import com.ossproj.donjjul.config.CharacterConfig;
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.enums.CharacterStage;
import com.ossproj.donjjul.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {
    private final UserRepository userRepo;
    private final DonationService donationService; // 기부 처리 서비스

    public CharacterService(UserRepository userRepo,
                            DonationService donationService) {
        this.userRepo = userRepo;
        this.donationService = donationService;
    }

    @Transactional
    public void processGoodConsumption(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        // 1) 포인트 적립
        user.setDonationPoints(user.getDonationPoints() + CharacterConfig.POINT_PER_CONSUMPTION);

        // 2) 단계 갱신
        int pts = user.getDonationPoints();
        if (pts >= CharacterConfig.ADULT_THRESHOLD) {
            user.setCharacterStage(CharacterStage.ADULT);
        } else if (pts >= CharacterConfig.CHILD_THRESHOLD) {
            user.setCharacterStage(CharacterStage.CHILD);
        } else {
            user.setCharacterStage(CharacterStage.BABY);
        }

        // 3) 성체(ADULT) 완성 처리
        if (user.getCharacterStage() == CharacterStage.ADULT) {
            donationService.donate(user, CharacterConfig.DONATION_AMOUNT);
            // 포인트와 단계 초기화
            user.setDonationPoints(0);
            user.setCharacterStage(CharacterStage.BABY);
        }

        userRepo.save(user);
    }
}
