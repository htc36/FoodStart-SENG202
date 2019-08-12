Feature: ImportExport feature.  
    Scenario: Importing data successfully (FR1) 
        Given The manager needs to import ingredients into the system 
        When The XML file for ingredients is imported complies with the DTD 
        Then The data has been successfully imported 

    Scenario: Importing data unsuccessfully (FR1) 
        Given The manager needs to import ingredients into the system 
        When The XML file for ingredients is imported does not comply with the DTD 
        Then The manager is informed that the XML file does not comply with the DTD 

    Scenario: Importing existing data (FR1) 
        Given The manager needs to import ingredients into the system 
        When An element being imported already exists in the system 
        Then That element is skipped 
 
    Scenario: Exporting data (FR2) 
        Given The manager needs to export a sales log 
        When The manager selects between two dates 
        Then The log is exported as an XML 
