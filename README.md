# pact
An example project showing how to use Pact between 2 services.

## To Execute ##

The general steps for setup and execution using a pact broker are as follows:

1. Consumer writes a Pact test. 
2. Consumer uploads the contract to Pact Broker. 
3. Producer runs Pact validation. 
4. CI/CD runs tests before deployment.

This is *consumer-driven* Pact Testing, whereby changes made by the Consumer affect the expected
contract between the services.

### How this example works ###
#### Consumer ####
The Consumer service is a spring boot consumer, using a RestTemplate to make a call to the Producer
service (/user) to get a list of users. We can set a Pact test to check that what we expect out of the 
API is what we actually get and we can then publish that as a contract to our broker to say that we agree 
to that contract.

#### Producer ####
The Producer service is a Spring Boot controller, returning the users available in the body. For the sake of
testing Pact, we can edit the users returned to include extra fields which should be invalid.
Where an update breaks existing functionality, we should update the versioning.


#### Docker Compose ####
The docker compose file sets up a postgres database and a Pact server at http://localhost:9292. There is a dependency
on postgres being set up before the Pact server starts, so there should be no race condition present.

To run the docker compose file, just execute `docker-compose up` with your Docker Desktop environment active.

This will set everything up in a window, and show the Pact log. At this point your broker is active.


### How to actually run it ###

To actually run the test, execute `./gradlew consumer:test` which will execute the initial Pact consumer test. The
pact files output from this test will be placed in the consumer's build/pacts directory, where you can see what a pact
.json file looks like.

Once the test has been run, execute `./gradlew consumer:pactPublish` to publish to the broker. If you open a browser
window at http://localhost:9292 you should be able to see the pacts published, and that this test has been added, albeit
in an unverified state. That means the consumer has created a pact but the provider hasn't necessarily confirmed that it
adheres to it yet.

Switch to the Producer module. Here, we have the actual Spring Controller endpoint, returning a list of users. Execute
the test by running `./gradlew producer:test`. This will pull the pact from the server and check that the controllers in
the module adhere to the pacts expected by the consumer. Once we're happy these are satisfied, we can run the
`./gradlew producer:pactVerify` command to set the verification in the pact server.

### In a Pipeline ###
We should run Pact tests in a CI/CD pipeline to check that a Producer conforms to the expectations of the Consumer(s). To
do this, we can add the gradlew commands above to a github workflow or gitlab-ci.yml. A github workflow might look something
like the following:

```yml
- name: Setup Gradle
  uses: gradle/actions/setup-gradle@417ae3ccd767c252f5661f1ace9f835f9654f2b5 # v3.1.0
  with:
    gradle-version: '8.5'

- name: Pact Test
  run: gradle producer:test
```

This setup currently has postgres and pact setup in a docker compose. Again, the expectations for a pipeline are that these
would be static, so postgres could be in RDS and the Pact server deployed as its own service. If, for whatever reason, the
database loses the existing Pacts, these would need to be created again by the consumer.

NOTE: Pacts are needed between APIs to form contracts. In Kafka, where a schema registry is used, a Pact is not needed. The schema
registry acts as a pact broker already, checking that when a consumer or producer is trying to serialize or deserialize event
data, it pulls the expected schema to do it.