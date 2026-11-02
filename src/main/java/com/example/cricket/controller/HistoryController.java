package com.example.cricket.controller;

import com.example.cricket.model.UserMatchHistory;
import com.example.cricket.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/me")
public class HistoryController {

    @Autowired
    private HistoryRepository historyRepository;

    @PostMapping("/history/{matchId}")
    public ResponseEntity<?> recordView(@PathVariable String matchId, Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        UserMatchHistory history = new UserMatchHistory(userId, matchId, OffsetDateTime.now());
        historyRepository.save(history);
        return ResponseEntity.ok("View recorded");
    }

    @GetMapping("/history")
    public ResponseEntity<List<UserMatchHistory>> getHistory(Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        List<UserMatchHistory> history = historyRepository.findByUserId(userId);
        return ResponseEntity.ok(history);
    }
}
