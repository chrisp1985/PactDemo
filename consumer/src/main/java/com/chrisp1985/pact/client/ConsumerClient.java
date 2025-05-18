package com.chrisp1985.pact.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class ConsumerClient {

    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    @Autowired
    public ConsumerClient(ObjectMapper objectMapper, RestClient restClient) {
        this.objectMapper = objectMapper;
        this.restClient = restClient;
    }

    public String getUsers() {

        var response = restClient.get()
                .uri("/v1/user")
                .retrieve()
                .toEntity(String.class)
                .getBody();

        try {
            log.debug("Response : {}", objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException ignored) {
            log.debug("Response (JsonProcessingException) : {}", response);
        }
        return response;
    }
}
