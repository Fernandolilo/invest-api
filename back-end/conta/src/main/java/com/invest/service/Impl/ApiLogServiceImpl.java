package com.invest.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.invest.dto.ApiLogDTO;
import com.invest.service.ApiLogService;

@Service
public class ApiLogServiceImpl implements ApiLogService {

    // Lista para armazenar os logs em memória
    private final List<ApiLogDTO> logs = new ArrayList<>();

    @Override
    public void addLog(ApiLogDTO log) {
        logs.add(log); // adiciona um log na lista
    }

    @Override
    public List<ApiLogDTO> getLogs() {
        return new ArrayList<>(logs); // retorna uma cópia da lista
    }

    @Override
    public void clearLogs() {
        logs.clear(); // limpa todos os logs
    }

}
