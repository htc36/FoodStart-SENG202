Feature: Inventory feature
    Scenarios involving Ingredients

	# Scenarios relating to viewing the ingredients in the inventory (UC7)
	
    @skip_scenario # Manually Tested
    Scenario: Viewing the ingredients in the inventory (UC7)
        Given There are 2 ingredients in the inventory
        When The inventory is displayed
        Then The ID, name, unit of measurement, truck stock, kitchen stock and dietary requirements for all 2 ingredients are displayed
        
    @skip_scenario # Manually Tested
	Scenario: View inventory when there are 0 ingredients in the inventory (UC7)
    	Given There are 0 ingredients in the inventory
    	When The inventory is displayed
    	Then The user is notified that there are no ingredients currently in the inventory

	# Scenario relating to editing the different fields of an ingredient (UC12)
	
	@skip_scenario # Manually tested
	Scenario: Editing the attributes of an ingredient in the inventory (UC12)
		Given There are 3 ingredients in the inventory
		And The ingredient "Edam Cheese" is in the inventory
		And The ingredient's ID number is 0
		When The attributes of the ingredient with ID 0 is edited
		Then It will either be the same or changed to their edited values
		
	# Scenarios relating to removing ingredients from the inventory (UC12)
	
	@skip_scenario # Manually tested
	Scenario: Removing an ingredient that is currently part of a recipe (UC12)
		Given The ingredient "Edam Cheese" is in the inventory
		And The ingredient "Edam Cheese" is used in the recipe "Small Edam Cheeseburger"
		When The ingredient "Edam Cheese" is manually removed from the inventory
		Then The ingredient "Edam Cheese" no longer exists in the inventory
		And The recipe "Small Edam Cheeseburger" no longer contains the ingredient "Edam Cheese"
		
	@skip_scenario # Manually tested
	Scenario: Attempting to remove an ingredient when there are no ingredients in the inventory (UC12)
		Given There are 0 ingredients in the inventory 
		When An attempt is made to remove an ingredient
		Then The user is notified that no ingredient has been selected to remove

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

	# Scenarios relating to adding ingredients into the inventory (UC12)

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
    
	# Scenarios relating to decreasing ingredient stock quantity when an order is placed that uses the ingredient
        
    Scenario Outline: Decreasing the truck stock quantity of ingredients in the inventory when orders are placed (UC4)
    	Given There are 3 ingredients in the inventory
    	And The ingredient <ingredientName> is in the inventory
    	And Its truck stock is <truckStock>
    	When An order is placed that needs <count> of the ingredient <ingredientName>
    	Then The truck stock for <ingredientName> is <newTruckStock>
    	Examples:
    	| ingredientName 	| truckStock 	| count | newTruckStock |
    	| "Peanut Butter"	| 45			| 10	| 35			|
    	| "Cucumber"		| 100			| 15	| 85			|
		| "Mayo"        	| 20 			| 5     | 15            |


	


    	
 
