package com.example.cricket.repository;

import com.example.cricket.model.UserMatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<UserMatchHistory, UUID> {
    List<UserMatchHistory> findByUserId(UUID userId);
}