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

    private String name;          // 기부처 이름
    private String type;          // 단체 종류
    private String phoneNumber;   // 전화번호
    private String introduction;  // 소개 문구
    private String imageUrl;      // 썸네일 (선택)

    public DonationTarget() {}

    // 필요시 생성자, getter, setter 추가
}
