package com.ossproj.donjjul.service;

import com.ossproj.donjjul.config.CharacterConfig;
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.dto.CharacterResponse;
import com.ossproj.donjjul.enums.CharacterStage;
import com.ossproj.donjjul.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {
    private final UserRepository userRepo;
    private final DonationService donationService;

    public CharacterService(UserRepository userRepo,
                            DonationService donationService) {
        this.userRepo = userRepo;
        this.donationService = donationService;
    }

    @Transactional
    public CharacterResponse processGoodConsumption(Long userId) {
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

        CharacterResponse resp;
        // 3) ADULT 달성 시 기부 & 초기화
        if (user.getCharacterStage() == CharacterStage.ADULT) {
            resp = donationService.donate(user, CharacterConfig.DONATION_AMOUNT);
            user.setDonationPoints(0);
            user.setCharacterStage(CharacterStage.BABY);
        } else {
            resp = new CharacterResponse(
                    user.getDonationPoints(),
                    user.getCharacterStage()
            );
        }

        userRepo.save(user);
        return resp;
    }
}
