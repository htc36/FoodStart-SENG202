Feature: Menu feature
    Scenarios involving the menu

    Scenario: Editing recipe price (UC11)
        Given A recipe "Small HotDog" exists
        When The price is changed to 7.50
        Then The recipe costs 7.5


    Scenario: Adding/removing ingredients from recipe (UC11)
        Given A recipe "Small HotDog" exists
        And The ingredient "wrap" is added with the unit quantity 1
        When The ingredient "bun" is removed
        Then The ingredients in the recipe "Small HotDog" are "wrap" and "sausage"

    Scenario: Editing recipe name and instuctions (UC11)
        Given A recipe "Small HotDog" exists
        And The recipe "Small HotDog" is changed to "AmericanWiener"
        When The recipe "AmericanWiener", instructions are changed to "Slot into bread hole"
        Then The recipe "AmericanWiener" has the instructions "Slot into bread hole"

    Scenario: Removing a recipe (UC11)
        Given A recipe "Small HotDog" exists
        When The recipe "Small HotDog" is removed
        Then The recipe "Small HotDog" does not exist

    Scenario: Menu Item display (UC2)
        Given A recipe "Small HotDog" exists
        And The menu item "HotDog" exists
        When The recipe "Small HotDog" is added to the "HotDog" menu item
        Then The corresponding recipe "Small HotDog" is displayed from "HotDog" menu item
        

    
    @skip_scenario # Manual testing
    Scenario: Specific menu display (UC1)
        Given An employee is looking through different menu sets
        When The employee selects a specific menu set
        Then The corresponding menu is displayed including name of menu and all recipes 
       
       
    @skip_scenario # Manual testing
    Scenario: Specific menu display (UC1)
        Given An employee is looking through different menu sets
        When The employee selects a specific menu set
        Then The corresponding menu is displayed including name of menu and all recipes
        
    @skip_scenario # Manual testing
    Scenario: All menu item display (UC2)
        Given An employee wants to see all the available menu items
        And There are 7 menu items in the system
        When The employee displays all menu items
        Then All 7 menu items are displayed with their names and the price of the default recipe
        
		@skip_scenario
		Scenario: Viewing all recipes (UC3) # Manual testing
	      Given An employee wants to see all the available recipes
	      And There are 12 recipes in the system
	      When The employee displays all recipes
	      Then All 12 recipes are displayed with their IDs, name, price, and the ingredients required
       
    @skip_scenario # Maunal testing
    Scenario: Menu editing (UC9)
        Given An employee is looking through different menu sets
        When The employee edits a specific menu set (i.e add and remove)
        Then The menu set is updated with the new information
        
    @skip_scenario # Manual testing
    Scenario: Menu Item editing (UC10)
        Given An employee is looking through menu items
        When The employee edits a specific a menu item (i.e the description, name and recipes)
        Then The corresponding menu item is updated with the new information
