package com.bafix.jobtracker.candidature;

import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CandidatureControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper;

    @Test
    void accesRefuseSansToken() throws Exception {
        mockMvc.perform(get("/candidatures"))
                .andExpect(status().isForbidden());
    }

    @Test
    void parcoursCompletInscriptionEtCreationDeCandidature() throws Exception {
        String inscriptionJson = objectMapper.writeValueAsString(Map.of(
                "email", "test@example.com",
                "password", "motdepasse123"
        ));

        String reponse = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inscriptionJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = objectMapper.readTree(reponse).get("token").asText();

        String candidatureJson = objectMapper.writeValueAsString(Map.of(
                "entreprise", "Capgemini",
                "poste", "Développeur Java",
                "statut", "ENVOYEE",
                "dateCandidature", "2026-08-14"
        ));

        mockMvc.perform(post("/candidatures")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidatureJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.entreprise").value("Capgemini"));

        mockMvc.perform(get("/candidatures")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].entreprise").value("Capgemini"));
    }
}