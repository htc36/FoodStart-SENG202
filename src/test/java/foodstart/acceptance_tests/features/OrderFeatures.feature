Feature: Order feature. 
    Scenarios involving orders 

    Scenario: Ordering a single item (FR11) 
        Given An employee is taking a customer’s order 
        When A hamburger is ordered 
        Then The total charge will be $5.00 

    Scenario: Ordering multiple items (FR11) 
        Given An employee is taking a customer’s order   
        When A hamburger and chips are ordered 
        Then The total charge will be $8.50 

    Scenario: Removing an item in the order (FR11) 
        Given A hamburger and chips are in the current order and costs $8.50 
        When Chips are removed from the order 
        Then Only the hamburger appears in the order and the total charge will be $5.00 

    Scenario: Editing ingredients in an item (FR11) 
        Given A customer does not want cheese in the hamburger 
        When Cheese is removed from the item 
        Then The hamburger is modified to have no cheese 

    Scenario: Ordering the same item more than once (FR11) 
        Given An employee is taking a customer’s order 
        When A hamburger is order 8 times 
        Then The total charge will be $40.00 

    Scenario: Editing an order (FR11) 
        Given An orange slushy is in the current order 
        When The flavour is edited to tropical 
        Then The system checks that it exists and replaces orange with tropical 

    Scenario: Customer has dietary requirements (FR11) 
        Given A customer with celiac disease and would like to know what items are gluten-free 
        When The employee selects gluten-free options 
        Then The items available are filtered to only show items that are gluten-free 

    Scenario: An item is sold out (FR11) 
        Given A customer wants a hamburger but is sold out 
        When The employee is about to order the item 
        Then The employee will not be able to place hamburger to the order 

    Scenario: Finalising an order (FR11) 
        Given The customer has placed all the items the customer has ordered 
        When The employee confirms the order  
        Then The customer is charged for the order and the order is recorded in the sales history 

    Scenario: Payment is overdue (FR11) 
        Given The total order costs $8.50 and the customer pays $10 
        When The payment is finalised 
        Then The customer receives $1.50 change 

    Scenario: Payment is under (FR11) 
        Given The total order costs $8.50 and the customer pays $5 
        When The payment is finalised 
        Then The employee is informed that the payment is short by $3.50 

    Scenario: View Sales Log (FR12) 
        Given The manager wants to see the sales log 
        When The log is shown 
        Then All details are displayed i.e time, items sold, amounts and price 

 
