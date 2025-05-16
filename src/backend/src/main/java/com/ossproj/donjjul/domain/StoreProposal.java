package com.ossproj.donjjul.domain;

import com.ossproj.donjjul.enums.ProposalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "business_number")
    private String businessNumber;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ProposalStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
