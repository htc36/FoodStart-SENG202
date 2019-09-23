Feature: Order feature
    Scenarios involving orders

  Scenario: Ordering a single item (FR11)
    Given A "hamburger" costs $5.00, which is a "permanent recipe"
    When The customer "Sally" orders 1 "hamburger" and pays by "cash"
    Then The customer will be charged $5.00 total

  Scenario: Ordering multiple items (FR11)
    Given A "hamburger" costs $5.00, which is a "permanent recipe" and "chips" costs $3.50, which is a "permanent recipe"
    When The customer "Sally" orders 1 "hamburger" and 1 "chips" and pays by "cash"
    Then The customer will be charged $8.50 total

  @skip_scenario
  Scenario: Removing an item in the order (FR11)
    Given A "hamburger" costs $5.00, which is a "permanent recipe" and "chips" costs $3.50, which is a "permanent recipe"
    And The current order has 1 "hamburger" and 1 "chips" with a current total of $8.50
    When 1 "chips" is removed from the order
    Then Only 1 "hamburger" appears in the order and does not contain "chips"
    And The customer will be charged $5.00 total

#    @skip_scenario
#    Scenario: Editing ingredients in an item (FR11)
#        Given A "hamburger" contains "cheese"
#        When The customer wants to remove "cheese" from the "hamburger"
#        Then The "hamburger" has no "cheese"

  Scenario: Ordering the same item more than once (FR11)
    Given A "hamburger" costs $5.00, which is a "permanent recipe"
    When The customer "Sally" orders 8 "hamburger" and pays by "eftpos"
    Then The customer will be charged $40.00 total

#    @skip_scenario
#    Scenario: Editing an order (FR11)
#        Given The current order has 1 "orange slushy"
#        When The flavour is edited to tropical
#        Then The system checks that it exists and replaces orange with tropical
#

    Scenario: Customer has dietary requirements (FR11)
        Given A customer wants to know if the "sandwich" is "gluten-free"
        When The employee checks the if "sandwich" is "gluten-free"
        Then The "sandwich" should be "gluten-free"

#    @skip_scenario
#    Scenario: An item is sold out (FR11)
#        Given A customer wants a hamburger but is sold out
#        When The employee is about to order the item
#        Then The employee will not be able to place hamburger to the order

#    @skip_scenario
#    Scenario: Finalising an order (FR11)
#        Given Customer "Sally" ordered 1 "hamburger"
#        And A "hamburger" costs $5.00
#        When The employee confirms the order
#        Then "Sally" will be charged $5.00 total
#        And The order is recorded in the sales history

#    @skip_scenario
#    Scenario: Payment is overdue (FR11)
#        Given The total order costs $8.50
#        And The customer pays $10.00
#        When The payment is finalised
#        Then The customer receives $1.50 change

#    @skip_scenario
#    Scenario: Payment is under (FR11)
#        Given The total order costs $8.50
#        And The customer pays $5.00
#        When The payment is finalised
#        Then The payment is short by $3.50

#    @skip_scenario
#    Scenario: View Sales Log (FR12)
#        Given Customer "Sally" ordered 1 "hamburger"
#        When The manager looks for "Sally" in the sales log
#        Then All details are displayed i.e time, items sold, amounts and price

 
