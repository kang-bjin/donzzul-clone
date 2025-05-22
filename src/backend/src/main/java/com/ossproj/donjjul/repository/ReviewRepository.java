// repository/ReviewRepository.java
package com.ossproj.donjjul.repository;

import com.ossproj.donjjul.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {}
