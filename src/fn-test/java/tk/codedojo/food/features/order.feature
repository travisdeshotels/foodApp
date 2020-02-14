Feature: Cancelling orders
    Cancel an order when it exists. Assert error when the order does not exist

    @HappyPath @skip
    Scenario: Cancel an existing order
        Given a restaurant exists
        And a customer exists
        And customer places an order
        Then customer cancels their order
        And a response of 200 is returned

    @SadPath @skip
    Scenario: Order does not exist
        Given a restaurant exists
        And a customer exists
        And the customer has no orders
        Then customer cancels their order
        And a response of 400 is returned
