package com.example.cricket.controller;

import com.example.cricket.model.LiveMatch;
import com.example.cricket.service.MatchService;
import org.springframework.web.bind.annotation.*;
import com.example.cricket.dto.MatchDetailDto;


import java.util.List;
@RestController
@RequestMapping("/api/matches") // Changed to /api/matches to match your frontend fetch
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/live")
    public List<LiveMatch> getLiveMatches(@RequestParam(defaultValue = "0") int offset) {
        return matchService.getLiveMatches(offset); 
    }

    // Updated to return the specific scoreboard data
    @GetMapping("/details/{matchId}")
    public MatchDetailDto getMatchDetails(@PathVariable String matchId) {
        return matchService.getMatchInfo(matchId); 
    }
}