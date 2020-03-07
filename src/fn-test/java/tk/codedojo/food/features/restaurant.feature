Feature: Rename Restaurant
    Rename an existing restaurant.

    @HappyPath
    Scenario: Rename a restaurant
        Given restaurant test01 exists
        When test01 restaurant is renamed to test02
        Then a response of 200 is returned

    @SadPath
    Scenario: Assert error when the restaurant does not exist
        Given restaurant test03 does not exist
        When test01 restaurant is renamed to test02
        Then a response of 400 is returned
