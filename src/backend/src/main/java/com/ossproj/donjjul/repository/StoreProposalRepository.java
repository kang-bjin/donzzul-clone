package com.ossproj.donjjul.repository;

import com.ossproj.donjjul.domain.StoreProposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreProposalRepository extends JpaRepository<StoreProposal, Long> {

    boolean existsByBusinessNumber(String businessNumber); // ✅ 중복 체크용

    List<StoreProposal> findByUserId(Long userId);
}
