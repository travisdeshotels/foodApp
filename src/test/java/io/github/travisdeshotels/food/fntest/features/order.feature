@functional
Feature: Orders

    @HappyPath
    Scenario Outline: Create an order
        Given restaurant test01 exists
        And customer test01 exists
        When customer test01 orders <quantity> of "<item>" at "<price>"
        Then a response of 201 is returned
        Examples:
        | item           | price | quantity |
        | bean           | 1     | 1        |
        | slice of bread | 1.5   | 1        |
        | pea            | 10.1  | 2        |
        | sandwich       | 1     | 2        |

    @HappyPath
    Scenario: Cancel an existing order
        Given restaurant test01 exists
        And customer test01 exists
        And customer test01 places an order
        Then customer test01 cancels their order
        And a response of 200 is returned

    @SadPath
    Scenario: Cancel order that does not exist
        Given restaurant test01 exists
        And customer test02 exists
        And customer test02 has no orders
        Then customer test02 cancels their order
        And a response of 400 is returned

    @SadPath
    Scenario: Cancel order that has been completed
        Given restaurant test01 exists
        And customer test5 exists
        When customer test5 places an order
        And customer test5 completes their order
        Then customer test5 cancels their order
        And a response of 400 is returned

    @HappyPath
    Scenario: Complete an existing order
        Given restaurant test01 exists
        And customer test01 exists
        And customer test01 places an order
        Then customer test01 completes their order
        And a response of 200 is returned

    @SadPath
    Scenario: Complete order that does not exist
        Given restaurant test01 exists
        And customer test02 exists
        And customer test02 has no orders
        Then customer test02 completes their order
        And a response of 404 is returned
