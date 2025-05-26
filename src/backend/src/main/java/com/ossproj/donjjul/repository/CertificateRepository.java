package com.ossproj.donjjul.repository;

import com.ossproj.donjjul.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByUser_Id(Long userId);
}
