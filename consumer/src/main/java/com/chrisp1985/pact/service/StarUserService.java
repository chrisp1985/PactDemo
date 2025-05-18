package com.chrisp1985.pact.service;

import com.chrisp1985.pact.client.ConsumerClient;
import com.chrisp1985.pact.data.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StarUserService {

    ConsumerClient client;
    private ObjectMapper objectMapper;

    @Autowired
    public StarUserService(ConsumerClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public List<User> getStarUsers() {
        String response = this.client.getUsers();
        List<User> users;

        try {
            users = objectMapper.readValue(response, new TypeReference<List<User>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return makeStarEmployee(users);
    }

    private List<User> makeStarEmployee(List<User> users) {
        return users.stream()
                .map(user -> new User(user.name() + " STAR EMPLOYEE", user.location(), user.age()))
                .toList();
    }
}
