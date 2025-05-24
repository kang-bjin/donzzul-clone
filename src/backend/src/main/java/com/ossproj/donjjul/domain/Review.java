// domain/Review.java
package com.ossproj.donjjul.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id")
    private Store store;

    private int rating;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Review() {}
    public Review(User user, Store store, int rating, String content) {
        this.user = user;
        this.store = store;
        this.rating = rating;
        this.content = content;
    }

    public Long getId() { return id; }
    public Long getUserId() { return user.getId(); }
    public Long getStoreId() { return store.getId(); }
    public int getRating() { return rating; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public User getUser() { return user; }
    public Store getStore() { return store; }
}
