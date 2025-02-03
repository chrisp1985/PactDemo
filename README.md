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


#### TODO ####
Run Consumer :test
Run Consumer :pactPublish (Pushes to the broker)

Run Producer :test
Run Producer :pactVerify (Makes it verified in the broker)