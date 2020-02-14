Feature: Rename Restaurant
    Rename an existing restaurant.

    @HappyPath @skip
    Scenario: Rename a restaurant
        Given a restaurant exists
        When restaurant is renamed
        Then a response of 200 is returned

    @SadPath @skip
    Scenario: Assert error when the restaurant does not exist
        Given no restaurants exist
        When restaurant is renamed
        Then a response of 400 is returned
