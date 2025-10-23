package com.invest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.dto.AuthenticationDTO;
import com.invest.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping()
	public ResponseEntity<Map<String, String>> authenticateAndGetToken(
	        @RequestBody AuthenticationDTO request,
	        HttpServletResponse response) {
	    final String token = authService.fromAuthentication(request);

	    // Header
	    response.addHeader("Authorization", "Bearer " + token);

	    // Body
	    Map<String, String> body = new HashMap<>();
	    body.put("token", token);

	    return ResponseEntity.ok(body);
	}

	
	 @GetMapping("/roles")
	    public ResponseEntity<String> getUserRoles() {
	        String roles = authService.fromAuthorization();
	        return ResponseEntity.ok(roles);
	    }

	
}
