package com.invest.dto;

import java.util.List;

public record HealthMonitorResponse(
        int totalLogs,
        int totalErros,
        List<String> errosEncontrados,
        List<String> servicosDown,
        boolean sistemaEstavel
) {}
