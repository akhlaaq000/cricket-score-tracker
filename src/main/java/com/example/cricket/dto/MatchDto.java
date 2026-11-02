package com.example.cricket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDto {
    private String id;
    private String name;
    private String matchType;
    private String status;
    private String venue;
    private String date;
    private String dateTimeGMT;
}