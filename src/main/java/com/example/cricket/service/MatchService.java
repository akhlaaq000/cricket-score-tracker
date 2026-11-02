package com.example.cricket.service;

import com.example.cricket.dto.CricketApiResponse;
import com.example.cricket.dto.EventDto;
import com.example.cricket.dto.StageDto;
import com.example.cricket.model.LiveMatch;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.cricket.dto.MatchDetailDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private static final Logger LOGGER = Logger.getLogger(MatchService.class.getName());
    private final ExternalApiClient externalApiClient;

    public MatchService(ExternalApiClient externalApiClient) {
        this.externalApiClient = externalApiClient;
    }

    @Cacheable(value = "liveMatches", key = "#offset")
    public List<LiveMatch> getLiveMatches(int offset) {
        try {
            // Standardize offset logic
            int apiOffset = Math.max(0, offset);
            
            // Fetch the new nested response from the LiveScore API
            CricketApiResponse response = externalApiClient.fetchCurrentMatches(apiOffset);
            
            if (response == null || response.getStages() == null) {
                LOGGER.warning("No data or stages received from LiveScore API");
                return Collections.emptyList();
            }

            // Flatten logic: Extract Events from all Stages and map to LiveMatch
            return response.getStages().stream()
                    .flatMap(stage -> stage.getEvents().stream()
                            .map(event -> mapToLiveMatch(stage, event)))
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            LOGGER.severe("Error processing live matches: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Maps the new API's Stage and Event data to the flat LiveMatch model used by your dashboard.
     */
    private LiveMatch mapToLiveMatch(StageDto stage, EventDto event) {
        try {
            LiveMatch liveMatch = new LiveMatch();
            
            // 1. Set ID (from Eid)
            liveMatch.setMatchId(event.getMatchId());
            
            // 2. Build Match Name (Team 1 vs Team 2)
            String t1 = (event.getTeam1() != null && !event.getTeam1().isEmpty()) 
                        ? event.getTeam1().get(0).getName() : "TBD";
            String t2 = (event.getTeam2() != null && !event.getTeam2().isEmpty()) 
                        ? event.getTeam2().get(0).getName() : "TBD";
            liveMatch.setMatchName(t1 + " vs " + t2);
            
            // 3. Set Status (Using the Score Summary 'ECo' or Status 'EpsL')
            String status = (event.getScoreSummary() != null && !event.getScoreSummary().isEmpty()) 
                            ? event.getScoreSummary() : event.getStatus();
            liveMatch.setStatus(status);
            
            // 4. Set Venue/Series Info
            liveMatch.setVenue(stage.getSeriesName());
            
            // 5. Set Match Type (Category name like 'Twenty20 Intl.')
            liveMatch.setMatchType(stage.getCategoryName());
            
            return liveMatch;
        } catch (Exception e) {
            LOGGER.severe("Error mapping event " + event.getMatchId() + ": " + e.getMessage());
            return null;
        }
    }

    public MatchDetailDto getMatchInfo(String matchId) {
        try {
            if (matchId == null || matchId.trim().isEmpty()) {
                throw new IllegalArgumentException("Match ID cannot be null or empty");
            }
            return externalApiClient.fetchMatchInfo(matchId);
        } catch (Exception e) {
            LOGGER.severe("Error fetching match info: " + e.getMessage());
            throw new RuntimeException("Failed to fetch match info: " + e.getMessage(), e);
        }
    }
}