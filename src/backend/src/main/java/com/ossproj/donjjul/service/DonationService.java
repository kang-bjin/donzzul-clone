package com.ossproj.donjjul.service;

import org.springframework.stereotype.Service;
import com.ossproj.donjjul.domain.User;

@Service
public class DonationService {
    public void donate(User user, int amount) {
        // 실제 기부 로직 구현 (외부 API 호출, DB 기록 등)
        // 예) 기부 레코드 저장, 사용자에게 알림
    }
}
