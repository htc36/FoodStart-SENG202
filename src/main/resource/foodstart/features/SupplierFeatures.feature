Feature: Supplier feature
    Some example scenarios involving suppliers

    @skip_scenario # Manually tested
    Scenario: View suppliers in the supplier list(UC8)
        Given There are 2 suppliers in the suppliers list
        When The suppliers list is displayed
        Then The code, name, phone number, phone type, email, website and address for all 2 suppliers are displayed

    @skip_scenario # Manually tested
    Scenario: View suppliers when there are no suppliers in the suppliers list (UC8)
        Given There are 0 suppliers in the suppliers list
        When The suppliers list is displayed
        Then The user is notified that there are no suppliers currently in the inventory

    # Scenarios relating to the adding of suppliers

    Scenario Outline: Adding suppliers to the suppliers list (UC13)
        Given There are 0 suppliers in the suppliers list
        And Supplier with code <code> does not exist in the supplier list
        And Its code is <code>
        And Its name is <name>
        And Its phone number is <phone>
        And Its phone type is <type>
        And Its email is <email>
        And Its website is <url>
        And Its address is <address>
        When Supplier with code <code> is manually added to the supplier list
        Then It will be stored with code <code> in the supplier list
        And Its name will be <name>
        And Its phone number will be <phone>
        And Its phone type will be <type>
        And Its email will be <email>
        And Its website will be <url>
        And Its address will be <address>
        Examples:
        | code | name           | phone         | type   | email                        | url                       | address               |
        | 123  | "Fresh Choice" | "03-355 4432" | "WORK" | ""                           | "www.freshchoice.co.nz"   | "189 Papanui Rd"      |
        | 124  | "Woolworths"   | "0314361378"  | "WORK" | "admin@woolworths.co.nz"     | ""                        | "124 Market Place"    |
        | 125  | "Countdown"    | "03-375 0092" | "WORK" | ""                           |  ""                       | "44 Count Street"     |
        | 1807 | "Pak'n'Slave"  | "025031807"   | "WORK" | "trade@paknslave.com"        | "www.paknslave.com"       | "25 Traders Road"     |


    @skip_scenario # Manually tested
    Scenario: Adding a supplier with blank required fields (UC13)
        Given The supplier "Original Name" does not exist in the suppliers list
        When The user attempts to add a new supplier with the name "Original Name" without specifying the phone number
        Then The user is notified that there is required information that is missing

    # Scenarios relating to the removing of suppliers

    @skip_scenario # Manually tested
    Scenario: Removing a supplier when there are no suppliers (UC13)
        Given There are 0 suppliers in the suppliers list
        And An attempt is made to remove a supplier
        Then The user is notified that no supplier has been selected to remove

    Scenario: Removing a supplier from the suppliers list (UC13)
        Given There are 2 suppliers in the suppliers list
        And Supplier with code 0 exists in the supplier list
        When Supplier with code 0 is manually removed from the supplier list
        Then Supplier with code 0 will not exist in the supplier list

    Scenario Outline: Removing suppliers from the suppliers list (UC13)
        Given There are 4 suppliers in the suppliers list
        And Supplier with code <code> exists in the supplier list
        When Supplier with code <code> is manually removed from the supplier list
        Then Supplier with code <code> will not exist in the supplier list
        Examples:
        | code |
        | 0    |
        | 1    |
        | 2    |
        | 3    |

    # Scenarios relating to the editing of suppliers

    @skip_scenario # Manually tested
    Scenario: Editing a supplier when there are no suppliers (UC13)
        Given There are 0 suppliers in the suppliers list
        And An attempt is made to edit a supplier
        Then The user is notified that no supplier has been selected to edit

    Scenario Outline: Editing the editable attributes of suppliers from the suppliers list (UC13)
        Given There are 1 suppliers in the suppliers list
        And Supplier with code <code> exists in the supplier list
        And Its code is <code>
        And Its name is <name>
        And Its phone number is <phone>
        And Its phone type is <type>
        And Its email is <email>
        And Its website is <url>
        And Its address is <address>
        When The supplier with code <code> is edited to name <newName>, phone <newPhone>, type <newType>, email <newEmail>, website <newUrl>, address <newAddress>
        Then Its code will be <code>
        And Its name will be <newName>
        And Its phone number will be <newPhone>
        And Its phone type will be <newType>
        And Its email will be <newEmail>
        And Its website will be <newUrl>
        And Its address will be <newAddress>
        Examples:
        | code | name           | phone          | type     | email                     | url                   | address             | newName          | newPhone       | newType  | newEmail                  | newUrl                | newAddress            |
        | 0    | "Count Up"     | "0123456789"   | "WORK"   | "onetwothree@countup.com" | "www.countup.com"     | "1234 Five Road"    | "Count Up"       | "0123456789"   | "WORK"   | "onetwothree@countup.com" | "www.countup.com"     | "1234 Five Road"      |
        | 0    | "Count Up"     | "0123456789"   | "WORK"   | "onetwothree@countup.com" | "www.countup.com"     | "1234 Five Road"    | "Count Up"       | "0123456789"   | "WORK"   | ""                        | ""                    | "1234 Five Road"      |
        | 0    | "Count Up"     | "0123456789"   | "WORK"   | "onetwothree@countup.com" | "www.countup.com"     | "1234 Five Road"    | "Count Down"     | "1111111111"   | "HOME"   | "threetwoone@countup.com" | "www.countdown.com"   | "5432 One Road"       |
