Feature: Import feature. 

    Testing importing data 

    Scenario: Importing Data Successfully 
        Given The manager needs to import ingredients into the system 
        When The XML file for ingredients is imported complies with the DTD 
        Then The data has been successfully imported 
 
    Scenario: Importing Data Unsuccessfully 
        Given The manager needs to import ingredients into the system 
        When The XML file for ingredients is imported does not comply with the DTD 
        Then The manager is informed that the XML file does not comply with the DTD 

    Scenario: Importing existing data 
        Given The manager needs to import ingredients into the system 
        When An element being imported already exists in the system 
        Then That element is skipped    
