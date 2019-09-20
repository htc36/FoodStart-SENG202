Feature: Inventory feature. 
    Scenarios involving Ingredients 
    Scenario: View ingredients (Functional Requirement 13) 
        Given An employee wants to view all ingredients 
        When The ingredients list is displayed 
        Then All relevant information is shown, i.e. name, amount in each location (Truck & depot) 

    Scenario: Check ingredients that are low in quantity (FR13) 
        Given The list of ingredients are shown 
        When Ingredients are filtered out by stock 
        Then The ingredients will be displayed in ascending order of stocks available 

    Scenario: Adding an ingredient to the list (FR14) 
        Given Ingredient is not found in the list 
        When It is manually added 
        Then It will be stored in the list of ingredients 

    Scenario: Removing an ingredient (FR14) 
        Given The ingredient will no longer be needed 
        When It is removed from the list of ingredients 
        Then It is removed from menu items that contain the ingredient 

    Scenario: Make an item unavailable due to an ingredient being sold out 
        Given An ingredient is sold out and it is included in menu items 
        When A customer wants to order the item with an ingredient that is sold out 
        Then The employee will not be able to add the item to the order 

    Scenario: Editing inventory (FR16) 
        Given The manager wants to edit ingredients 
        When A specific item is selected, and the stock level is edited 
        Then The items stock level is changed, and the updated kitchen and depot stock is shown 

 
