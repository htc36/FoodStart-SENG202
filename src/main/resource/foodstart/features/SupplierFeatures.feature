Feature: Supplier feature
    Some example scenarios involving suppliers


    Scenario: Adding a supplier to the suppliers list (UC13)
        Given There are 2 suppliers in the suppliers list
        And Supplier with code 1807 does not exist in the supplier list
        And Its code is 1807
        And Its name is "Pak'n'Slave"
        And Its phone number is "025031807"
        And Its phone type is "WORK"
        And Its email is "trade@paknslave.com"
        And Its website is "www.paknslave.com"
        And Its address is "25 Traders Road"
        When Supplier with code 1807 is manually added to the supplier list
        Then It will be stored with code 1807 in the supplier list
        And Its name will be "Pak'n'Slave"
        And Its phone number will be "025031807"
        And Its phone type will be "WORK"
        And Its email will be "trade@paknslave.com"
        And Its website will be "www.paknslave.com"
        And Its address will be "25 Traders Road"

    @skip_scenario # Exception has not been created yet
    Scenario: Adding an existing supplier to the suppliers list (UC13)
        Given Supplier with code 12 exists in the supplier list
        And Its name is "Old World"
        And Its phone number is "01430414"
        And Its phone type is "MOBILE"
        And Its email is "awhole@oldworld.com"
        And Its website is "www.oldworld.com"
        And Its address is "123 Great Road"
        When Supplier with code 12 is manually added to the supplier list
        Then An ExistingSupplierException is thrown

    @skip_scenario
    Scenario: Removing a supplier from the suppliers list (UC13)
        Given There are 2 suppliers in the suppliers list
        And Supplier with code 1807 exists in the supplier list
        When Supplier with code 1807 is manually removed from the supplier list
        Then Supplier with code 1807 will not exist in the supplier list

    @skip_scenario
    Scenario: View suppliers (UC8)
        Given An employee wants to view the suppliers list
        And There are 2 suppliers in the suppliers list
        When The suppliers list is displayed
        Then The code, name, phone number, phone type, email, website and address for all 2 suppliers are displayed

    @skip_scenario
    Scenario: Missing fields (UC13)
        Given Supplier with code 123 does not exist in the supplier list
        And Its name is "Count Up"
        And Its phone number is ""
        And Its phone type is "WORK"
        And Its email is "onetwothree@countup.com"
        And Its website is "www.countup.com"
        And Its address is "1234 Five Road"
        When The supplier with code 123 is loaded
        Then An ImportFailureException is thrown
