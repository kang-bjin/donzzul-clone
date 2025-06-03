package com.ossproj.donjjul.controller;

import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.domain.Donation;
import com.ossproj.donjjul.domain.DonationTarget;
import com.ossproj.donjjul.dto.LoginRequest;
import com.ossproj.donjjul.dto.LoginResponse;
import com.ossproj.donjjul.repository.DonationRepository;
import com.ossproj.donjjul.repository.DonationTargetRepository;
import com.ossproj.donjjul.service.UserService;
import com.ossproj.donjjul.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final DonationRepository donationRepository;
    private final DonationTargetRepository donationTargetRepository;

    // 회원가입
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.createUser(user);
        return ResponseEntity.ok(saved);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return userService.login(request.getUsername(), request.getPassword())
                .map(user -> {
                    String token = jwtUtil.generateToken(String.valueOf(user.getId()));
                    return ResponseEntity.ok(new LoginResponse(token, user.getUsername(), user.getNickname()));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // username으로 유저 정보 조회
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 포인트 조회
    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(Map.of("points", user.getDonationPoints()));
    }

    // ✅ 포인트 증가 (활동 시 s호출됨)
    @PostMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> addPoints(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        int delta = body.get("delta");
        User user = userService.findById(id);
        user.addDonationPoints(delta);
        userService.save(user);
        return ResponseEntity.ok(Map.of("updatedPoints", user.getDonationPoints()));
    }

    // ✅ 기부 (ADULT일 때 호출됨)
    @PostMapping("/{userId}/donate")
    public ResponseEntity<?> donatePoints(
            @PathVariable Long userId,
            @RequestBody Map<String, Integer> payload) {
        int amount = payload.get("amount");
        userService.donatePoints(userId, amount);
        return ResponseEntity.ok().build();
    }


    // ✅ 유저 상태 전체 조회 (프론트 초기 진입 시 사용)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserInfo(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "nickname", user.getNickname(),
                "points", user.getDonationPoints(),
                "stage", user.getCharacterStage().name()
        ));
    }
}
