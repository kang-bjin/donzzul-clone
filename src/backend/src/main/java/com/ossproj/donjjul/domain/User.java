package com.ossproj.donjjul.domain;

import com.ossproj.donjjul.enums.CharacterStage;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nickname;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    private int donationPoints = 0;               // 누적 포인트

    @Enumerated(EnumType.STRING)
    private CharacterStage characterStage = CharacterStage.BABY;  // 캐릭터 단계

    public void addDonationPoints(int delta) {
        this.donationPoints += delta;
        updateCharacterStage();
    }

    public void subtractDonationPoints(int amount) {
        if (this.donationPoints < amount) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        this.donationPoints -= amount;
        updateCharacterStage();
    }

    public void updateCharacterStage() {
        if (donationPoints >= 10000) {
            this.characterStage = CharacterStage.ADULT;
        } else if (donationPoints >= 5000) {
            this.characterStage = CharacterStage.CHILD;
        } else {
            this.characterStage = CharacterStage.BABY;
        }
    }

}
