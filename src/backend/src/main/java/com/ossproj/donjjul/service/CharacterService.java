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

    public CharacterService(UserRepository userRepo) {
        this.userRepo = userRepo;
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

        userRepo.save(user);

        // 3) 현재 상태 반환
        return new CharacterResponse(
                user.getDonationPoints(),
                user.getCharacterStage()
        );
    }
}
