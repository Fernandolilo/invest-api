package com.invest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.dto.ApiLogDTO;
import com.invest.service.ApiLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class ApiLogController {

	 private final ApiLogService apiLogService;
	 	
	 	@GetMapping
	    public List<ApiLogDTO> getLogs() {
	        return apiLogService.getLogs();
	    }

	    /**
	     * Limpa todos os logs
	     */
	    @DeleteMapping("/clear")
	    public void clearLogs() {
	        apiLogService.clearLogs();
	    }
	 
}
