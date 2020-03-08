Feature: Orders

    @HappyPath
    Scenario: Cancel an existing order
        Given restaurant test01 exists
        And customer test01 exists
        And customer places an order
        Then customer test01 cancels their order
        And a response of 200 is returned

    @SadPath
    Scenario: Cancel order that does not exist
        Given restaurant test01 exists
        And customer test02 exists
        And customer test02 has no orders
        Then customer test02 cancels their order
        And a response of 400 is returned

    @HappyPath
    Scenario: Complete an existing order
        Given restaurant test01 exists
        And customer test01 exists
        And customer places an order
        Then customer test01 completes their order
        And a response of 200 is returned

    @SadPath
    Scenario: Complete order that does not exist
        Given restaurant test01 exists
        And customer test02 exists
        And customer test02 has no orders
        Then customer test02 completes their order
        And a response of 404 is returned
