# Could have a comment here, but usually the 
# features are self-descriptive.
Feature: Order feature.
    Some example scenarios involving orders

    Scenario: Hamburger order
        Given An employee is taking a customer’s order
        When A hamburger is ordered
        Then The total charge will be $5.00

    Scenario: Multiple orders
        Given An employee is taking a customer’s order  
        When A hamburger and chips is ordered
        Then The total charge will be $8.50

    Scenario: 2x the same item
        Given An employee is taking a customer’s order
        When A 8x Hamburger
        Then THe total charge will be $40.00

        



