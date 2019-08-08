Feature: Ingredients feature. 
    Scenarios involving Ingredients 
    
    Scenario: Check ingredients that are low in quantity 
        Given The list of ingredients are shown 
        When Ingredients are filtered out by stock 
        Then The ingredients will be displayed in ascending order of stocks available 

    Scenario: Adding an ingredient to the list 
        Given Ingredients is not found in the list 
        When It is manually added 
        Then It will be stored in the list of ingredients 

    Scenario: Removing an ingredient 
        Given The ingredient will no longer be needed 
        When It is removed from the list of ingredients 
        Then It is removed from menu items that contain the ingredient 

    Scenario: Make an item unavailable due to an ingredient being sold out 
        Given A customer wants to make an order that is unavailable 
        When An order is placed with an ingredient that is sold out 
        Then The menu item is grey out and cannot be pressed for the ordering system. 
