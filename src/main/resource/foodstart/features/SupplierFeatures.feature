Feature: Supplier feature.  
    Some example scenarios involving suppliers

    @skip_scenario
    Scenario: Adding a supplier to the suppliers list (FR10)
        Given Supplier "Count Up" does not exist in the supplier list
        And Its code is 123
        And Its phone number is "0123456789"
        And Its phone type is "WORK"
        And Its email is "onetwothree@countup.com"
        And Its website is "www.countup.com"
        And Its address is "1234 Five Road"
        When Supplier "Count Up" is manually added to the supplier list
        Then It will be stored under "Count Up" in the supplier list

    @skip_scenario # Exception has not been created yet
    Scenario: Adding an existing supplier to the suppliers list (FR10)
        Given Supplier "Old World" exists in the supplier list
        And Its code is 12
        And Its phone number is "01430414"
        And Its phone type is "MOBILE"
        And Its email is "awhole@oldworld.com"
        And Its website is "www.oldworld.com"
        And Its address is "123 Great Road"
        When Supplier "Old World" is manually added to the supplier list
        Then An ExistingSupplierException is thrown

    @skip_scenario
    Scenario: Removing a supplier from the suppliers list (FR10)
        Given Supplier "Pak'n'Slave" exists in the supplier list
        And Its code is 1807
        And Its phone number is "0800123456"
        And Its phone type is "WORK"
        And Its email is "store@paknslave.com"
        And Its website is "www.oldworld.com"
        And Its address is "187 Traders Road"
        When Supplier "Pak'n'Slave' is manually removed from the supplier list
        Then It is removed from the database and cannot be viewed on the list

    @skip_scenario
    Scenario: View suppliers (FR16)
        Given An employee wants to view the suppliers list
        And There are 2 suppliers in the suppliers list
        When The suppliers list is displayed
        Then The code, name, phone number, phone type, email, website and address for all 2 suppliers are displayed

    @skip_scenario
    Scenario: Missing fields (FR9)
        Given Supplier "Count Up" does not exist in the supplier list
        And Its code is 123
        And Its phone number is null
        And Its phone type is "WORK"
        And Its email is "onetwothree@countup.com"
        And Its website is "www.countup.com"
        And Its address is "1234 Five Road"
        When The "Count Up" is loaded
        Then An ImportFailureException is thrown
