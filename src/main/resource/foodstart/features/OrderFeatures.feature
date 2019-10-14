Feature: Order feature
    Scenarios involving orders

   # Scenarios relating to adding a single item in an order

  Scenario: Ordering a single item (UC4)
    Given A "hamburger" costs $5.00, which is a "permanent recipe"
    When The customer "Sally" orders 1 "hamburger" and pays by "cash"
    Then The customer will be charged $5.00 total

  Scenario: Ordering multiple items (UC4)
    Given A "hamburger" costs $5.00, which is a "permanent recipe" and "chips" costs $3.50, which is a "permanent recipe"
    When The customer "Sally" orders 1 "hamburger" and 1 "chips" and pays by "cash"
    Then The customer will be charged $8.50 total

  Scenario: Removing an item in the order (UC4)
    Given A "hamburger" costs $5.00, which is a "permanent recipe" and "chips" costs $3.50, which is a "permanent recipe"
    And The current order has 1 "hamburger" and 1 "chips" with a current total of $8.50
    When 1 "chips" is removed from the order
    Then Only 1 "hamburger" appears in the order and does not contain "chips"
    And The customer will be charged $5.00 total

  Scenario: Ordering the same item more than once (UC4)
    Given A "hamburger" costs $5.00, which is a "permanent recipe"
    When The customer "Sally" orders 8 "hamburger" and pays by "eftpos"
    Then The customer will be charged $40.00 total

  Scenario: Customer has dietary requirements and the menu item meets the requirement (UC4)
    Given A customer wants to know if the "sandwich" is "gluten-free"
    When The employee checks the if "sandwich" is "gluten-free"
    Then The "sandwich" should be "gluten-free"

  Scenario: Customer has dietary requirements and the menu item does not the requirement (UC4)
    Given A customer wants to know if the "sandwich" is "vegan", but it is not
    When The employee checks the if "sandwich" is "vegan"
    Then The "sandwich" should not be "vegan"

  @skip_scenario #Manually tested
  Scenario: Removing ingredients in an item (UC5)
      Given A "Chicken Burger" contains "Edam Cheese"
      When The customer wants to remove "Edam Cheese" from the "Chicken Burger"
      Then The "Chicken Burger" has no "Edam Cheese"

  @skip_scenario # Manually tested
    Scenario: Adding ingredients in an item (UC5)
      Given A "Chicken Burger" does not already contain "Egg"
      When The customer wants to add 1 quantity of "Egg" to the "Chicken Burger"
      Then The "Chicken Burger" will have 1 quantity of "Egg" added

  @skip_scenario # Manually tested
  Scenario: Editing an item in the order to be one of the available variants (UC4)
      Given The current order has 1 "Small Edam Cheeseburger"
      When "Small Edam Cheeseburger" is edited to "Large Edam Cheeseburger"
      Then The current order has 1 "Large Edam Cheeseburger

  @skip_scenario #Manually tested
    Scenario: Increasing the quantity of an item in the order (UC4)
      Given The current order has 1 "Baked Beans"
      When The quantity of "Baked Beans" is increased to 10
      Then The current order will contain 10 "Baked Beans"

  @skip_scenario #Manually tested
  Scenario: Decreasing the quantity of an item in the order (UC4)
    Given The current order has 5 "Baked Beans"
    When The quantity of "Baked Beans" is increased to 3
    Then The current order will contain 3 "Baked Beans"

  @skip_scenario #Manually tested
  Scenario: Decreasing the quantity of an item in the order where its quantity is 1 (UC4)
    Given The current order has 1 "Baked Beans"
    When The quantity of "Baked Beans" is decreased to 0
    Then The system does not allow the item to be decreased further

  @skip_scenario # Manually tested
  Scenario: An ingredient used is completely sold out (UC4)
      Given A "Small Edam Cheeseburger" contains "Edam Cheese"
      And There is 0 "Edam Cheese" in the truck stock
      When The employee tries to add the order
      Then The employee will not be able to place "Small Edam Cheeseburger" to the order
      
  @skip_scenario # Manually tested
  Scenario: Insufficient quantity of an ingredient used in the recipe (UC4)
      Given A "Small Edam Cheeseburger" contains "Edam Cheese"
      And There is 99 "Edam Cheese" in the truck stock
      And The recipe "Hamburger" requires 100 "Edam Cheese"
      When The employee tries to add the order
      Then The employee will be notified that there is insufficient stock for "Edam Cheese"
      And The employee can choose to add the order with just the current stock for "Edam Cheese" of 99

  @skip_scenario # Manually tested
  Scenario: Finalising an order (UC4)
      Given Customer "Sally" ordered 1 "Chicken Burger"
      And A "Chicken Burger" costs $5.50
      When The employee confirms the order
      Then "Sally" will be charged $5.50 total
      And The order is recorded in the sales history

  @skip_scenario # Manually tested
  Scenario: Viewing an order in the sales log (UC6)
      Given Customer "Sally" ordered 1 "Chicken Burger"
      When The manager looks for "Sally" in the sales log
      Then All details are displayed i.e time, items sold, amounts and price

