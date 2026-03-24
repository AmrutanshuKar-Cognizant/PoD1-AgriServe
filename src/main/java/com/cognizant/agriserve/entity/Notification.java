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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Entity
@Table(name="notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    public enum NotificationCategory {
        ADVISORY,
        TRAINING,
        FEEDBACK,
        COMPLIANCE
    }

    public enum NotificationStatus {
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


}
