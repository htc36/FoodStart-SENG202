Feature: Menu feature
    Scenarios involving the menu

  @skip_scenario
   Scenario: Viewing recipe (UC3)
        Given The recipe "Hamburger" is not in the menu
        And Its price is 14.0
        And Its ingredients are 2 "Patty"s and 1 "Bun"
        And Its id is 5
        And Its instructions are "Put pattys in bun"
        When Recipe "Hamburger" is manually added
        Then "Hamburger" that costs 14.0 with 2 "Patty"s and 1 "Bun", id being 5, instructions being "Put pattys in bun"

    Scenario: Editing recipe price (UC11)
        Given A recipe "HotDog" exists
        When The price is changed to 7.50
        Then The recipe costs 7.5


    Scenario: Adding/removing ingredients from recipe (UC11)
        Given A recipe "HotDog" exists
        When The ingredient "wrap" is added with the unit quantity 1
        When The ingredient "bun" is removed
        Then The ingredients in the recipe "HotDog" are "wrap" and "sausage"

    Scenario: Editing recipe name and instuctions (UC11)
        Given A recipe "HotDog" exists
        When The recipe "HotDog" is changed to "AmericanWiener"
        When The recipe "AmericanWiener", instructions are changed to "Slot into bread hole"
        Then The recipe "AmericanWiener" has the instructions "Slot into bread hole"

    Scenario: Removing a recipe (UC11)
        Given A recipe "HotDog" exists
        When The recipe "HotDog" is removed
        Then The recipe "HotDog" does not exist




    @skip_scenario
    Scenario: Menu Item display (UC2)
        Given An employee is looking through menu items
        When The employee selects a specific menu item
        Then The corresponding recipes (Vegan, GF etc) of the menu item are displayed
    @skip_scenario
    Scenario: Menu Item editing (UC10)
        Given An employee is looking through menu items
        When The employee edits a specific a menu item (i.e the description, name and recipes)
        Then The corresponding menu item is updated with the new information
    @skip_scenario
    Scenario: Menu display (UC1)
        Given An employee is looking through different menu sets
        When The employee selects a specific menu set
        Then The corresponding menu is displayed including name of menu and all recipes
    @skip_scenario
    Scenario: Menu editing (UC9)
        Given An employee is looking through different menu sets
        When The employee edits a specific menu set (i.e add and remove)
        Then The menu set is updated with the new information
