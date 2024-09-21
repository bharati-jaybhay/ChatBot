package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ChatBotService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_KEY = "AIzaSyBdS0frjinweez70E9HAfbFzQpH5WReGpg";

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyBdS0frjinweez70E9HAfbFzQpH5WReGpg"
            + API_KEY;

    public String callApi(String prompt) {
        String apiUrl = API_URL_TEMPLATE;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", prompt);
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("API call failed with status code: " + e.getStatusCode(), e);
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Failed to access the API endpoint", e);
        }
    }
}
