package com.ossproj.donjjul.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DonationTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;      // 기부처 이름
    private String description;
    private String imageUrl;  // (선택) 프론트에 보여줄 썸네일

    public DonationTarget() {}
}
