package com.ossproj.donjjul.service;

import com.ossproj.donjjul.domain.Certificate;
import com.ossproj.donjjul.domain.Review;
import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.dto.CertificateResponse;
import com.ossproj.donjjul.repository.CertificateRepository;
import com.ossproj.donjjul.repository.ReviewRepository;
import com.ossproj.donjjul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final UserRepository userRepo;
    private final ReviewRepository reviewRepo;
    private final CertificateRepository certRepo;

    @Transactional
    public void issueCertificate(Long userId, Long reviewId) {
        User user = userRepo.findById(userId).orElseThrow();
        Review review = reviewRepo.findById(reviewId).orElseThrow();
        certRepo.save(new Certificate(user, review));
    }

    @Transactional(readOnly = true)
    public List<CertificateResponse> getCertificates(Long userId) {
        return certRepo.findByUser_Id(userId).stream()
                .map(CertificateResponse::from)
                .collect(Collectors.toList());
    }
}
