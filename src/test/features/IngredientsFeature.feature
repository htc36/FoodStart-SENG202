# Could have a comment here, but usually the
# features are self-descriptive.
Feature: Ingredients feature.
    Some example scenarios involving orders

    Scenario: Basic burger
        Given The truck is open for business
        When I order a beefburger
        Then I will be charged $10

        Scenario: Multiple items
        Given The truck is open for business
        When I order a cheeseburger and chips
        Then I will be charged $18.50
