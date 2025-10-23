package com.invest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiLogDTO {

	private String method;
    private String path;
    private int status;
    private long durationMs;
    private long duration;
    private String ip;
    private String requestBody;
    private LocalDateTime timestamp;
}
