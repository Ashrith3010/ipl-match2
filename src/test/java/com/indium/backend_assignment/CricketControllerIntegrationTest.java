package com.indium.backend_assignment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CricketControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testUploadJsonFile() throws IOException {
        String url = "http://localhost:" + port + "/api/cricket/upload";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Load the test file
        ClassPathResource fileResource = new ClassPathResource("testdata.json");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity(url, requestEntity, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("File uploaded successfully", response.getBody());
    }

    @Test
    public void testGetMatchesPlayedByPlayer() {
        String playerName = "Sachin";
        String url = "http://localhost:" + port + "/api/cricket/matches/player/" + playerName;

        String response = testRestTemplate.getForObject(url, String.class);
        assertEquals("Matches played by Sachin: [list of matches]", response);  // Replace with actual expected response
    }

    @Test
    public void testGetCumulativeScoreOfPlayer() {
        String playerName = "Sachin";
        String url = "http://localhost:" + port + "/api/cricket/score/player/" + playerName;

        Integer response = testRestTemplate.getForObject(url, Integer.class);
        assertEquals(15000, response);
    }

    @Test
    public void testGetTopBatsmenPaginated() {
        String url = "http://localhost:" + port + "/api/cricket/batsmen/top?page=0&size=5";

        String response = testRestTemplate.getForObject(url, String.class);
        assertEquals("Top Batsmen: [list of batsmen]", response);  // Replace with actual expected response
    }

    /*
    @Test
    public void testGetMatchScoresByDate() {
        String date = "2022-12-31";
        String url = "http://localhost:" + port + "/api/cricket/matches/date/" + date;

        String response = testRestTemplate.getForObject(url, String.class);
        assertEquals("Scores on 2022-12-31: [list of scores]", response);  // Replace with actual expected response
    }
    */
}
