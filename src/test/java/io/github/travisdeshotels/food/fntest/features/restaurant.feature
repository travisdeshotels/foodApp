@functional
Feature: Restaurants

    @HappyPath
    Scenario: Update menu
        Given restaurant test01 exists
        When test01 menu is updated
        Then a response of 200 is returned

    @SadPath
    Scenario: Menu is empty
        Given restaurant test01 exists
        When test01 menu is updated without a menu
        Then a response of 404 is returned

    @HappyPath
    Scenario: Rename a restaurant
        Given restaurant test01 exists
        When test01 restaurant is renamed to test02
        Then a response of 200 is returned
        And test02 restaurant is renamed to test01

    @SadPath
    Scenario: Rename restaurant that does not exist
        Given restaurant test03 does not exist
        When test03 restaurant is renamed to test02
        Then a response of 400 is returned

    @SadPath
    Scenario: Rename restaurant name already exists
        Given restaurant test04 exists
        And restaurant test05 exists
        When test04 restaurant is renamed to test05
        Then a response of 400 is returned
