Feature: Supplier feature.
    Some example scenarios involving suppliers

    Scenario: Missing email
        Given Supplier Funky Kumquat has no URL
        When The Funky Kumquat is loaded
        Then a default URL is assigned