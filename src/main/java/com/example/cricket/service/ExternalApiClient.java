package com.example.cricket.service;

import com.example.cricket.dto.CricketApiResponse;
import com.example.cricket.dto.MatchDetailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.logging.Logger;

@Service
public class ExternalApiClient {

    private static final Logger LOGGER = Logger.getLogger(ExternalApiClient.class.getName());
    private final RestTemplate restTemplate;

    @Value("${cricket.api.key}")
    private String apiKey;

    public ExternalApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CricketApiResponse fetchCurrentMatches(int offset) {
        try {
            String url = "https://livescore6.p.rapidapi.com/matches/v2/list-live?Category=cricket";
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", apiKey);
            headers.set("X-RapidAPI-Host", "livescore6.p.rapidapi.com");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            return restTemplate.exchange(url, HttpMethod.GET, entity, CricketApiResponse.class).getBody();
        } catch (Exception e) {
            LOGGER.severe("Error fetching list: " + e.getMessage());
            return null;
        }
    }

    public MatchDetailDto fetchMatchInfo(String matchId) {
        try {
            String url = "https://livescore6.p.rapidapi.com/matches/v2/get-scoreboard?Category=cricket&Eid=" + matchId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", apiKey);
            headers.set("X-RapidAPI-Host", "livescore6.p.rapidapi.com");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(url, HttpMethod.GET, entity, MatchDetailDto.class).getBody();
        } catch (Exception e) {
            LOGGER.severe("Error fetching scoreboard: " + e.getMessage());
            return null;
        }
    }
}