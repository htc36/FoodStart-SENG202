Feature: Menu feature. 
    Scenarios involving the menu 

    Scenario: Viewing recipe (FR3) 
        Given An employee wants to see a recipe 
        When The employee selects a recipe 
        Then The corresponding recipe is displayed 

    Scenario: Editing recipe (FR4) 
        Given An employee is looking through recipes 
        When The employee edits a specific recipe 
        Then The recipe is updated in the system 

    Scenario: Menu Item display (FR5) 
        Given An employee is looking through menu items 
        When The employee selects a specific menu item 
        Then The corresponding recipes (Vegan, GF etc) of the menu item are displayed  
 
    Scenario: Menu Item editing (FR6) 
        Given An employee is looking through menu items 
        When The employee edits a specific a menu item (i.e the description, name and recipes) 
        Then The corresponding menu item is updated with the new information 

    Scenario: Menu display (FR7) 
        Given An employee is looking through different menu sets 
        When The employee selects a specific menu set 
        Then The corresponding menu is displayed including name of menu and all recipes 

    Scenario: Menu editing (FR8) 
        Given An employee is looking through different menu sets 
        When The employee edits a specific menu set (i.e add and remove) 
        Then The menu set is updated with the new information 
