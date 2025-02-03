package com.chrisp1985.pact;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.target.Target;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;
import com.chrisp1985.pact.controller.UserController;
import com.chrisp1985.pact.service.UserService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // Ensures app runs on an actual port
@PactBroker(url = "http://localhost:9292") // Use Pact Broker
@Provider("UserService")
public class UserProducerPactTest {

    @Autowired
    private UserController userController;

    @MockitoBean
    private UserService userService;

    @TestTarget // Annotation denotes Target that will be used for tests
    public final HttpTestTarget target = new HttpTestTarget("localhost", 8082, "/v1/user"); //Test Target

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
        System.setProperty("pact.provider.version", "1.2");
        System.setProperty("pact.verifier.publishResults", "true");
    }

    @BeforeEach
    void beforeEach(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost",
                8082));
    }

    @State("users are available")
    void usersAreAvailable() {}

    @State("no users are available")
    void noUsersAreAvailable() {}

}
