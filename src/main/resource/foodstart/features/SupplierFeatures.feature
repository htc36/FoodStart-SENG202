Feature: Supplier feature.  
    Some example scenarios involving suppliers 
#
#    Scenario: Missing fields (FR9)
#        Given Supplier "Funky Kumquat" has no URL
#        When The "Funky Kumquat" is loaded
#        Then A notification is given
#
#    Scenario: Adding a supplier (FR10)
#        Given Supplier "Funky Kumquat" does not exist in the supplier list
#        When A user adds a supplier "Funky Kumquat"
#        Then "Funky Kumquat" appears in the supplier list
#
#    Scenario: Adding an existing supplier (FR10)
#        Given The manager needs to add a supplier manually
#        When The supplier being added already exists
#        Then A notification is given, and the supplier is not added
#
#    Scenario: Removing a supplier (FR10)
#        Given The supplier is no longer be needed
#        When It is removed from the list of suppliers
#        Then It is removed from the database and cannot be viewed on the list
#
#    Scenario: View suppliers (FR16)
#        Given The manager wants to view the suppliers
#        When a specific supplier is selected
#        Then Relevant information is shown such as ingredients supplied by supplier
