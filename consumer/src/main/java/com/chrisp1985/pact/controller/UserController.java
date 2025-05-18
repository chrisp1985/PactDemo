package com.chrisp1985.pact.controller;

import com.chrisp1985.pact.data.User;
import com.chrisp1985.pact.service.StarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    StarUserService starUserService;

    @GetMapping(value = "")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok(starUserService.getStarUsers());

    }
}
