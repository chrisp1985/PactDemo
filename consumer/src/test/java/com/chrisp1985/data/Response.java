package com.chrisp1985.data;

import com.chrisp1985.pact.data.User;

import java.util.List;

public record Response(List<User> users) {
}
