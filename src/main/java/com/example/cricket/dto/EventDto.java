package com.example.cricket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
    @JsonProperty("Eid")
    private String matchId;
    
    @JsonProperty("EpsL")
    private String status; // e.g., "Play in progress"
    
    @JsonProperty("ECo")
    private String scoreSummary; // e.g., "Sri Lanka are 91 for 4..."
    
    @JsonProperty("T1")
    private List<TeamDto> team1;
    
    @JsonProperty("T2")
    private List<TeamDto> team2;

    @Data
    public static class TeamDto {
        @JsonProperty("Nm")
        private String name;
        @JsonProperty("Abr")
        private String abbreviation;
    }
}