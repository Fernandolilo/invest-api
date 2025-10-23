package com.invest.service;

import java.util.List;

import com.invest.dto.ApiLogDTO;

public interface ApiLogService {

	 void addLog(ApiLogDTO log);

	    List<ApiLogDTO> getLogs();

	    void clearLogs();
}
