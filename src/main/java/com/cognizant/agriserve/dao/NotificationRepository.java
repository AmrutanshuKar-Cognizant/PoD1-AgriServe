package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Fetch all notifications for a specific user
    List<Notification> findByUserId(Long userId);

    // Fetch notifications for a user based on status (e.g., only UNREAD notifications)
    List<Notification> findByUserIdAndStatus(Long userId, Notification.NotificationStatus status);

    // Fetch all notifications related to a specific workshop or session
    List<Notification> findByEntityId(Long entityId);

    // Fetch notifications for a specific user filtered by category
    List<Notification> findByUserIdAndCategory(Long userId, Notification.NotificationCategory category);
}