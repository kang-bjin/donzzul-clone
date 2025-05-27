package com.ossproj.donjjul.domain;

import com.ossproj.donjjul.domain.DonationTarget;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;
    private LocalDateTime donatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private DonationTarget target;

    public Donation(User user, DonationTarget target, int amount) {
        this.user = user;
        this.target = target;
        this.amount = amount;
        this.donatedAt = LocalDateTime.now();
    }

    public Donation() {}
}
