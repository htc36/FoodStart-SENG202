Feature: Inventory feature
    Scenarios involving Ingredients

    @skip_scenario # Manually Tested
    Scenario: View inventory(UC7)
        Given There are 2 ingredients in the inventory
        And An employee wants to view the 2 ingredients stored inventory
        When The inventory is displayed
        Then The ID, name, truck stock, kitchen stock and dietary requirements for all 2 ingredients are displayed


    Scenario: Adding an ingredient to the list (UC12)
        Given That ingredient "Relish" is not found in the inventory
        And Its unit type is "GRAMS"
        And Its kitchen stock is 200
        And Its truck stock is 50
        And Its dietary requirement is "VEGAN,VEGETARIAN"
        When Ingredient "Relish" is manually added to the inventory
        Then It will be stored under "Relish" in the inventory
        And The unit type for "Relish" is "GRAMS"
        And The kitchen stock for "Relish" is 200
        And The truck stock for "Relish" is 50
        And The dietary requirement for "Relish" will have "VEGAN,VEGETARIAN"
       
       
    Scenario Outline: Adding ingredients to the list (UC12)
    	Given That ingredient <ingredientName> is not found in the inventory
    	And Its unit type is <unitType>
    	And Its kitchen stock is <kitchenStock>
    	And Its truck stock is <truckStock>
    	And Its dietary requirement is <dietaryRequirements>
    	When Ingredient <ingredientName> is manually added to the inventory
    	Then It will be stored under <ingredientName> in the inventory
    	And The unit type for <ingredientName> is <unitType>
    	And The kitchen stock for <ingredientName> is <kitchenStock>
    	And The truck stock for <ingredientName> is <truckStock>
    	And The dietary requirement for <ingredientName> will have <dietaryRequirements>
    	Examples:
    	| ingredientName 	| unitType    	| kitchenStock 	| truckStock | dietaryRequirements 												|
    	| "Sausage"		 	| "UNITS"       | 1000         	| 100        | "NUT_ALLERGY"													|
    	| "Baked Beans"    	| "UNITS"       | 5000	      	| 250        | "VEGAN,VEGETARIAN,NUT_ALLERGY,GLUTEN_FREE,LACTOSE_INTOLERANT"	|
    	| "Garlic"         	| "GRAMS"       | 3800         	| 1500       | "VEGAN,VEGETARIAN,NUT_ALLERGY,GLUTEN_FREE,LACTOSE_INTOLERANT"	|
    	| "Milk"           	| "MILLILITRES" | 10410        	| 6056       | "VEGETARIAN,NUT_ALLERGY"											|
    
    
    


    Scenario: Removing an ingredient (UC12)
        Given There are 3 ingredients in the inventory
        And The ingredient "Cucumber" is in the inventory
        And The ingredient's ID number is 3
        When The ingredient "Cucumber" is manually removed
        Then The ingredient ID 3 no longer exists in the inventory


    Scenario Outline: Removing ingredients (UC12)
        Given There are 3 ingredients in the inventory
        And The ingredient <ingredientName> is in the inventory
        And The ingredient's ID number is <id>
        When The ingredient <ingredientName> is manually removed
        Then The ingredient ID <id> no longer exists in the inventory
        Examples:
        | ingredientName | id |
        | "Cucumber"     | 3  |
        | "Peanut Butter"| 2  |
        | "Mayo"         | 1  |



    Scenario: Decreasing the truck stock quantity of an ingredient in the inventory when an order is placed (UC4)
        Given There are 2 ingredients in the inventory
        And The ingredient "Mayo" is in the inventory
        And Its truck stock is 20
        When An order is placed that needs 5 of the ingredient "Mayo"
        Then The truck stock for "Mayo" is 15


	
	# Scenarios relating to editing the different fields of an ingredient
	
	@skip_scenario # Manually tested
	Scenario: Editing the attributes of an ingredient in the inventory (UC12)
		Given THere are 3 ingredients in the inventory
		And The ingredient "Cucumber" is in the inventory"
		And The ingredient's ID number is 3
		When The ingredient with ID 3 has it's "Name,truck stock,kitchen stock" edited to be "Mushroom,300,500"
		Then It will be stored under "Mushroom" in the inventory
		And The truck stock for "Mushroom" is 300
		And The kitchen stock for "Mushroom" is 500

	@skip_scenario
    Scenario: Editing the name of an item in the inventory (UC12)
    	Given There are 3 ingredients in the inventory
    	And The ingredient "Cucumber" is in the inventory
    	And The ingredient's ID number is 3
    	When "Cucumber" is selected and edited so it's name is now "Gherkin"
    	Then It will be stored under "Gherkin" in the inventory
    	And The ID of "Gherkin" is 3
    	
	@skip_scenario
    Scenario: Editing the truck stock of an item in the inventory (UC12) 
        Given The ingredient "Cucumber" is in the inventory
        And Its truck stock is 10
        When "Cucumber" is selected and edited so it now has 300 in the truck stock
        Then The ingredient "Cucumber" will have a truck stock of 300
        
 
