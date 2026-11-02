package com.example.cricket.controller;

import com.example.cricket.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/analytics")
public class AdminAnalyticsController {

    private final AnalyticsService analyticsService;

    @Autowired
    public AdminAnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/matches")
    public Map<String, Long> getMatchAnalytics() {
        long totalViews = analyticsService.getTotalMatchViews();
        return Map.of("totalMatchViews", totalViews);
    }

    @GetMapping("/users")
    public Map<String, Long> getUserAnalytics() {
        long dailyActiveUsers = analyticsService.getDailyActiveUsers();
        return Map.of("dailyActiveUsers", dailyActiveUsers);
    }
}