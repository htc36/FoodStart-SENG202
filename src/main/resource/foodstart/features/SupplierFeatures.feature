Feature: Supplier feature
    Some example scenarios involving suppliers

    # Scenarios relating to the adding of suppliers

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


    @skip_scenario # Manually tested
    Scenario: Adding a supplier with blank required fields (UC13)
        Given The supplier "Original Name" does not exist in the suppliers list
        When The user attempts to add a new supplier with the name "Original Name" without specifying the phone number
        Then The user is notified that there is required information that is missing

    # Scenarios relating to the removing of suppliers

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

    Scenario: Editing the editable attributes of a supplier from the suppliers list (UC13)
        Given There are 2 suppliers in the suppliers list
        And Supplier with code 1 exists in the supplier list
        And Its code is 1
        And Its name is "Under Value"
        And Its phone number is "01111111"
        And Its phone type is "WORK"
        And Its email is "super@undervalue.com"
        And Its website is "www.undervalue.com"
        And Its address is "11 Costly Road"
        When The supplier with code 1 is edited to name "New Name", phone "123454321", type "MOBILE", email "old@newname.com", website "newname.com", address "1 New Street"
        Then Its code will be 1
        And Its name will be "New Name"
        And Its phone number will be "123454321"
        And Its phone type will be "MOBILE"
        And Its email will be "old@newname.com"
        And Its website will be "newname.com"
        And Its address will be "1 New Street"

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



    @skip_scenario
    Scenario: View suppliers (UC8)
        Given An employee wants to view the suppliers list
        And There are 2 suppliers in the suppliers list
        When The suppliers list is displayed
        Then The code, name, phone number, phone type, email, website and address for all 2 suppliers are displayed


