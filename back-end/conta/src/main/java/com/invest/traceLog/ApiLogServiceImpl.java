package com.invest.traceLog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.invest.dto.ApiLogDTO;
import com.invest.service.ApiLogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiLogServiceImpl implements ApiLogService {

    private final List<ApiLogDTO> logs = new ArrayList<>();

    @Override
    public void addLog(ApiLogDTO logEntry) {

        logs.add(logEntry);

        analisarStatus(logEntry);
    }

    private void analisarStatus(ApiLogDTO logEntry) {

        int status = logEntry.getStatus();

        // 🎯 Monitorar tudo que não for sucesso (2xx)
        if (status < 200 || status >= 300) {

            if (status >= 500) {
                log.error("🚨 ERRO CRÍTICO | {} {} | Status {} | IP {}",
                        logEntry.getMethod(),
                        logEntry.getPath(),
                        status,
                        logEntry.getIp());

            } else {
                log.warn("⚠️ ERRO HTTP | {} {} | Status {} | IP {}",
                        logEntry.getMethod(),
                        logEntry.getPath(),
                        status,
                        logEntry.getIp());
            }
        }
    }

    @Override
    public List<ApiLogDTO> getLogs() {
        return new ArrayList<>(logs);
    }

    @Override
    public void clearLogs() {
        logs.clear();
    }
}
