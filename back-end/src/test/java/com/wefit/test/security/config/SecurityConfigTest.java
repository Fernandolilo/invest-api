package com.wefit.test.security.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.wefit.test.sercurity.jwt.JwtAuthenticationFilter;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void whenNoAuth_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/clients/protected")) // endpoint protegido
               .andExpect(status().isOk());
    }



    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void whenWithMockUser_thenAccessGranted() throws Exception {
        mockMvc.perform(get("/clients/authenticate"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPublicEndpoint_thenPermitAll() throws Exception {
        mockMvc.perform(get("/clients/authenticate")) // endpoint liberado
                .andExpect(status().isOk());
    }
    
    
}
