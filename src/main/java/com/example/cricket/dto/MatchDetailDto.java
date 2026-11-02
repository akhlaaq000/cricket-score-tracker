package com.example.cricket.dto;

// THESE ARE THE MISSING IMPORTS CAUSING YOUR ERRORS
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDetailDto {
    @JsonProperty("Eid")
    private String matchId;
    
    @JsonProperty("ECo")
    private String statusCommentary;
    
    @JsonProperty("EpsL")
    private String matchStatus;

    @JsonProperty("Tr1C1") private String team1Runs;
    @JsonProperty("Tr1CW1") private String team1Wickets;
    @JsonProperty("Tr1CO1") private String team1Overs;

    @JsonProperty("Tr2C1") private String team2Runs;
    @JsonProperty("Tr2CW1") private String team2Wickets;
    @JsonProperty("Tr2CO1") private String team2Overs;

    @JsonProperty("T1") private List<TeamDto> team1;
    @JsonProperty("T2") private List<TeamDto> team2;
    
    @JsonProperty("Venue") private VenueDto venue;
    
    @JsonProperty("Ebat") private int battingTeam;

    @Data
    public static class TeamDto {
        @JsonProperty("Nm") private String name;
        @JsonProperty("Abr") private String abbreviation;
    }

    @Data
    public static class VenueDto {
        @JsonProperty("Vnm") private String name;
    }
}