package com.example.cricket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StageDto {
    @JsonProperty("Snm")
    private String seriesName; // e.g., "Sri Lanka v England"
    
    @JsonProperty("Cnm")
    private String categoryName; // e.g., "Twenty20 Internationals"
    
    @JsonProperty("Events")
    private List<EventDto> events;
}