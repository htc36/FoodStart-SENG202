Feature: Menu feature. 
    Scenarios involving the menu 

    @skip_scenario
    Scenario: Viewing recipe (FR3)
        Given The recipe "Hamburger" is not in the menu
        And Its price is 14.0
        And Its ingredients are 2 "Patty"s and 1 "Bun"
        And Its id is 5
        And Its instructions are "Put pattys in bun"
        When Recipe "Hamburger" is manually added
        Then "Hamburger" that costs 14.0 with 2 "Patty"s and 1 "Bun", id being 5, instructions being "Put pattys in bun"
#    @skip_scenario
#    Scenario: Editing recipe (FR4)
#        Given An employee is looking through recipes
#        When The employee edits a specific recipe
#        Then The recipe is updated in the system
#    @skip_scenario
#    Scenario: Menu Item display (FR5)
#        Given An employee is looking through menu items
#        When The employee selects a specific menu item
#        Then The corresponding recipes (Vegan, GF etc) of the menu item are displayed
#    @skip_scenario
#    Scenario: Menu Item editing (FR6)
#        Given An employee is looking through menu items
#        When The employee edits a specific a menu item (i.e the description, name and recipes)
#        Then The corresponding menu item is updated with the new information
#    @skip_scenario
#    Scenario: Menu display (FR7)
#        Given An employee is looking through different menu sets
#        When The employee selects a specific menu set
#        Then The corresponding menu is displayed including name of menu and all recipes
#    @skip_scenario
#    Scenario: Menu editing (FR8)
#        Given An employee is looking through different menu sets
#        When The employee edits a specific menu set (i.e add and remove)
#        Then The menu set is updated with the new information
