Feature: Health Check
    Check that the application is running

    Scenario: Check application health with the health check endpoint
        Given the application is running
        When I try the healthcheck endpoint
        Then I get a response of 200
