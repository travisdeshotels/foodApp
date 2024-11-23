Feature: Health Check
    Check that the application is running

    @functional
    Scenario: Check application health with the health check endpoint
        Given the application is running
        When I try the healthcheck endpoint
        Then a response of 200 is returned
