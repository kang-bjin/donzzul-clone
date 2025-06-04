// dto/ReviewCreateRequest.java
package com.ossproj.donjjul.dto;

public class ReviewCreateRequest {
    private Long storeId;
    private int rating;
    private String content;
    private Long userId; // 추가

    // Getter/Setter
    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getUserId() { return userId; } // 추가
    public void setUserId(Long userId) { this.userId = userId; } // 추가
}
