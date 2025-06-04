package com.ossproj.donjjul.dto;

import com.ossproj.donjjul.domain.Review;

import java.time.LocalDateTime;

public class ReviewResponse {
    private Long id;
    private Long userId;
    private Long storeId;
    private int rating;
    private String content;
    private LocalDateTime createdAt;

    public ReviewResponse(Long id, Long userId, Long storeId,
                          int rating, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getStoreId() { return storeId; }
    public int getRating() { return rating; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getUser().getId(),
                review.getStore().getId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
