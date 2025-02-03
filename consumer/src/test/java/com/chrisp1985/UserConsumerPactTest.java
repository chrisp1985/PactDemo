package com.chrisp1985;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Map;
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "UserService", pactVersion = PactSpecVersion.V3)
public class UserConsumerPactTest {

    @Pact(consumer = "UserService")
    RequestResponsePact getAvailableUserDetails(PactDslWithProvider builder) {
        DslPart body = PactDslJsonArray.arrayEachLike()
                .integerType("age")
                .stringType("name")
                .stringType("location")
                .closeObject();
        return builder
                    .given("users are available")
                    .uponReceiving("some user details")
                    .method("GET")
                    .path("/v1/user")
                    .willRespondWith()
                    .status(200)
                    .headers(Map.of("Content-Type", "application/json"))
                    .body(body)

                .given("no users are available")
                    .uponReceiving("no user details")
                    .method("GET")
                    .path("/v1/user")
                    .willRespondWith()
                    .status(200)
                    .headers(Map.of("Content-Type", "application/json"))
                    .body("[]")
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserService", pactMethod = "getAvailableUserDetails", pactVersion = PactSpecVersion.V3)
    void getUserDetailsWhenUsersAreAvailable(MockServer mockServer) {
        String baseUrl = mockServer.getUrl();

        Response response = RestAssured.given()
                .when()
                .get(baseUrl + "/v1/user");

        Assertions.assertEquals(200, response.getStatusCode());

    }

    @Test
    @PactTestFor(providerName = "UserService", pactMethod = "getAvailableUserDetails", pactVersion = PactSpecVersion.V3)
    void getUserDetailsWhenNoUsersAreAvailable(MockServer mockServer) {
        String baseUrl = mockServer.getUrl();

        Response response = RestAssured.given()
                .when()
                .get(baseUrl + "/v1/user");

        Assertions.assertEquals(200, response.getStatusCode());

    }
}
