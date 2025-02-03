package com.chrisp1985.pact.service;

import com.chrisp1985.pact.data.User;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class UserService {

    List<User> users = new ArrayList<>();

    public UserService() {
        this.addDefaultUsers();
    }

    private void addDefaultUsers() {
        users = List.of(
                new User("Chris", "UK", 39),
                new User("Bob", "US", 56),
                new User("Ted", "NED", 22));
    }

}
