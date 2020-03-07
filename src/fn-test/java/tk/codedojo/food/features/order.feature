Feature: Cancelling orders
    Cancel an order when it exists. Assert error when the order does not exist

    @HappyPath
    Scenario: Cancel an existing order
        Given restaurant test01 exists
        And customer test01 exists
        And customer places an order
        Then customer test01 cancels their order
        And a response of 200 is returned

    @SadPath
    Scenario: Order does not exist
        Given restaurant test01 exists
        And customer test02 exists
        And customer test02 has no orders
        Then customer test02 cancels their order
        And a response of 400 is returned
