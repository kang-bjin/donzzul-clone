package com.ossproj.donjjul.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount; // 기부 금액

    private LocalDateTime donatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Donation() {}

    public Donation(User user, int amount) {
        this.user = user;
        this.amount = amount;
        this.donatedAt = LocalDateTime.now();
    }

    // getter
}
