Feature: Basket feature.
    Some example scenarios involving the basket

    Scenario: Single item in basket
        Given A black jersey is priced at $50
        When I add it to the basket
        Then The total basket price is $50