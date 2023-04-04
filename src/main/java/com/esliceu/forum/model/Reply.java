package com.esliceu.forum.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Reply(){

    }

    public Reply(String content, Instant createdAt, Instant updatedAt, User user, Topic topic) {
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.topic = topic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
