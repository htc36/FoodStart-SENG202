Feature: Order feature. 
    Scenarios involving orders 

    Scenario: Ordering a single item 
        Given An employee is taking a customer’s order 
        When A hamburger is ordered 
        Then The total charge will be $5.00 

    Scenario: Ordering multiple items 
        Given An employee is taking a customer’s order   
        When A hamburger and chips are ordered 
        Then The total charge will be $8.50 

    Scenario: Removing an item in order 
        Given A hamburger and chips are in the current order and costs $8.50 
        When Chips are removed from the order 
        Then Only the hamburger appears in the order and costs $5.00 

    Scenario: Ordering the same item more than once 
        Given An employee is taking a customer’s order 
        When A hamburger is order 8 times 
        Then The total charge will be $40.00 

    Scenario: Editing order 
        Given An orange slushy is in the current order 
        When The flavour is edited to tropical 
        Then The system checks that it exists and replaces orange with tropical 

    Scenario: Customer with dietary requirements: 
        Given A customer with celiac disease and would like to know what items are gluten-free 
        When The employee selects gluten-free options 
        Then The items available are filtered to only show items that are gluten-free 

    Scenario: Item is sold out 
        Given A customer wants a hamburger but is sold out 
        When The employee is about to order the item 
        Then The button to order the hamburger is greyed out so it is unavailable to order 

    Scenario: Finalising order 
        Given The customer has listed all they have wanted to order 
        When The employee confirms the order  
        Then The customer is charged for the order and the order is recorded in the sale history 

