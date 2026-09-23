package com.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogMonitorResponse {

    private int totalLogs;
    private int totalErros;
    private String ultimoErro;
    private boolean sistemaEstavel;
}
