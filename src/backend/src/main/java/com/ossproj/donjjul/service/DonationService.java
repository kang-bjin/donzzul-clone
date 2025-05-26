// DonationService.java
package com.ossproj.donjjul.service;

import com.ossproj.donjjul.dto.CharacterResponse;
import com.ossproj.donjjul.domain.User;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
    // 반환 타입을 CharacterResponse로 변경
    public CharacterResponse donate(User user, int amount) {
        // 1) 실제 기부 로직 (DB 저장, 외부 API 호출 등)
        //    예) donationRepository.save(new Donation(...));

        // 2) 기부 후 사용자 상태를 담은 DTO 반환
        return new CharacterResponse(
                user.getDonationPoints(),
                user.getCharacterStage()
        );
    }
}
