package com.ossproj.donjjul.repository;

import com.ossproj.donjjul.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
