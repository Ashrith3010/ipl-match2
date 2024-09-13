package com.indium.backend_assignment;

import com.indium.backend_assignment.controller.CricketController;
import com.indium.backend_assignment.service.CricketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CricketController.class)
public class CricketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CricketService cricketService;

    @Test
    public void uploadJsonFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json", "{}".getBytes());

        mockMvc.perform(multipart("/api/cricket/upload") // Corrected endpoint
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully"));
    }



    @Test
    public void getMatchesPlayedByPlayer() throws Exception {
        String playerName = "yuvaraj singh";
        String output = "Matches played by yuvaraj singh"; // Mocked response
        when(cricketService.getMatchesPlayedByPlayer(playerName)).thenReturn(output);
        String url = "/api/cricket/matches/player/" + playerName;
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(output));
    }

    @Test
    public void getCumulativeScoreOfPlayer() throws Exception {
        String playerName = "John";
        int score = 120;
        when(cricketService.getCumulativeScoreOfPlayer(playerName)).thenReturn(score);
        String url = "/api/cricket/score/player/" + playerName;
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(score)));
    }

    @Test
    public void getTopBatsmenPaginated() throws Exception {
        String output = "Top batsmen list"; // Mocked response
        when(cricketService.getTopBatsmenPaginated(any())).thenReturn(output);
        String url = "/api/cricket/batsmen/top";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(output));
    }
}
