Feature: ImportExport feature.  
    Scenario: Importing data successfully (FR1) 
        Given The manager imports an XML file
        And The file is of type "ingredients"
        When The file succeeds validation against the DTD
        And The file parses correctly
        Then The data has been imported successfully

    @skip_scenario
    Scenario: Displaying successfully imported data

    Scenario: Importing data unsuccessfully from non-compliant XML (FR1)
        Given The manager imports an XML file
        And The file is of type "ingredients"
        When The file does not succeed validation against the DTD
        Then The data has been imported unsuccessfully
        And The manger is informed of the import failure due to non-compliant XML

    Scenario: Importing existing data (FR1) 
        Given The manager needs to import ingredients into the system 
        When An element being imported already exists in the system 
        Then That element is skipped 
 
    Scenario: Exporting data (FR2) 
        Given The manager needs to export a sales log 
        When The manager selects between two dates 
        Then The log is exported as an XML 
