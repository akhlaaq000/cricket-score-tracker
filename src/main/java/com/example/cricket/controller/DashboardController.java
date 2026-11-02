package com.example.cricket.controller;

import com.example.cricket.model.LiveMatch;
import com.example.cricket.service.MatchService;
import com.example.cricket.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

    private final MatchService matchService;
    private final AnalyticsService analyticsService;

    public DashboardController(MatchService matchService, AnalyticsService analyticsService) {
        this.matchService = matchService;
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getDashboardData(
            Principal principal, 
            @RequestParam(defaultValue = "0") int offset) {
        try {
            String username = (principal != null) ? principal.getName() : "Guest";
            
            if (offset < 0) {
                offset = 0;
            }
            
            List<LiveMatch> matches = matchService.getLiveMatches(offset);
            long totalViews = analyticsService.getUserTotalViews(username);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("user", username);
            response.put("message", "Welcome to your cricket insights!");
            response.put("stats", Map.of(
                "matchesViewed", totalViews,
                "liveCount", matches != null ? matches.size() : 0
            ));
            response.put("matches", matches != null ? matches : Collections.emptyList());
            
            LOGGER.info("Dashboard accessed by user: " + username);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.severe("Error fetching dashboard data: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                "message", "Failed to fetch dashboard data",
                "error", e.getMessage()
            ));
        }
    }
}