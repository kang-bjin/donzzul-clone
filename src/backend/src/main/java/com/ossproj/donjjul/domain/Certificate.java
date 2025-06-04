package com.ossproj.donjjul.domain;

import com.ossproj.donjjul.domain.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Review review;  // 어떤 소비 내역 기반인지

    private LocalDate issuedDate;

    public Certificate(User user, Review review) {
        this.user = user;
        this.review = review;
        this.issuedDate = LocalDate.now();
    }
}
