package com.example.cricket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveMatch {
    private String matchId;
    private String matchName;
    private String team1;
    private String team2;
    private String status;
    private String series;
    private String venue; // Added this field to fix setVenue error
    private String matchType;
    private String startTime;
}