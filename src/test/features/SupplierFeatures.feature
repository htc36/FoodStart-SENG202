Feature: Supplier feature. 
    Some example scenarios involving suppliers 
    Scenario: Missing email 
        Given Supplier Funky Kumquat has no URL 
        When The Funky Kumquat is loaded 
        Then a notification is given 

    Scenario: Adding Supplier 
        Given There are no current suppliers 
        When A user adds a supplier 
        Then There is one supplier in the list 

    Scenario: Adding existing supplier 
        Given The manager needs to add a supplier manually 
        When The supplier being added already exists 
        Then A notification is given, and the supplier is not added 

    Scenario: Removing a supplier 
        Given The supplier is no longer be needed 
        When It is removed from the list of suppliers 
        Then It is removed from the database and cannot be viewed on the list 
