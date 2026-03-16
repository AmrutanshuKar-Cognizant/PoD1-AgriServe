package com.cognizant.agriserve.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;



@Entity
@Table(name="notification")
public class Notification {
    enum NotificationCategory {
        ADVISORY,
        TRAINING,
        FEEDBACK,
        COMPLIANCE
    }

    enum NotificationStatus {
        UNREAD,
        READ
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    // The user receiving the notification (e.g., a Farmer or an Officer)
    @Column(nullable = false)
    private Long userId;

    // Soft Foreign Key pointing to the specific WorkshopID, SessionID, etc.
    @Column(nullable = false)
    private Long entityId;

    @Column(nullable = false, length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    // Default Constructor
    public Notification() {
    }

    // Parameterized Constructor
    public Notification(Long userId, Long entityId, String message, NotificationCategory category) {
        this.userId = userId;
        this.entityId = entityId;
        this.message = message;
        this.category = category;
    }

    // Automatically set the date and default status before saving to DB
    @PrePersist
    protected void onCreate() {
        if (this.createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = NotificationStatus.UNREAD;
        }
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationCategory getCategory() {
        return category;
    }

    public void setCategory(NotificationCategory category) {
        this.category = category;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
