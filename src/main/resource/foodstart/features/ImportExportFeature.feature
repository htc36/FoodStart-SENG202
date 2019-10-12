Feature: ImportExport feature
    Scenarios involving importing and exporting XML files

	# All of these scenario's are manually tested


	# Scenarios related to importing XML files
    @skip_scenario
    Scenario Outline: Importing a valid XML file (UC15)
        Given A file is of type <fileType>
        And The file is a valid XML file
        And Any objects that the XML references is already in the system
        When The user imports this file
        Then The data has been imported successfully
        And The data is displayed on its appropriate screen
        Examples:
        | fileType 		|
        | "INGREDIENT"	|
        | "RECIPE"		|
        | "MENU"		|
        | "SUPPLIER"	|
        | "SALES_LOG"	|


	@skip_scenario
	Scenario Outline: Importing data uncessfully due to referenced objects not being in the system (UC15)
		Given A file is of type <fileType>
		And The file is a valid XML file
		And An object that is referenced in the XML file is not in the system
		When The user imports this file
		Then The data is not imported successfully
		And The user is notified that and error has occured while importing the file
		Examples:
		| fileType		|
		| "RECIPE"		|
		| "MENU"		|
		| "SALES_LOG"	|


    @skip_scenario
    Scenario Outline: Importing data unsuccessfully due to a non-compliant XML file (UC15)
		Given A file is of type <fileType>
		And The file does not comply to the DTD for <fileType>
		When The user imports this file
        Then The file does not succeed validation against the DTD
        And The data has been imported unsuccessfully
        And The user is informed of the import failure due to non-compliant XML
        Examples:
        | fileType		|
        | "INGREDIENT"	|
        | "RECIPE"		|
        | "MENU"		|
        | "SUPPLIER"	|
        | "SALES_LOG"	|


    @skip_scenario
    Scenario Outline: Importing existing data that already exists in the system (UC15)
		Given A file is of type <fileType>
		And An element of the file already exists in the system
		When The user imports this file
        Then That element that already exists in the system is overwritten by what is imported
       	Examples:
       	| fileType		|
       	| "INGREDIENT"	|
       	| "RECIPE"		|
       	| "MENU"		|
       	| "SUPPLIER"	|
       	| "SALES_LOG"	|
        
        
    # Scenarios related to exporting XML files

    @skip_scenario
    Scenario Outline: Exporting data (UC14)
        Given There is data that the user wants to export
        And The data is of type <dataType>
        When The user chooses to export this data
        Then The data is exported to a file called <fileName>
        Examples:
        | dataType 		| fileName			|
        | "INGREDEIENT"	| "ingredients.xml" |
        | "RECIPE"		| "recipes.xml"		|
        | "MENU"		| "menu.xml"		|
        | "SUPPLIER"	| "suppliers.xml" 	|
        | "SALES_LOG"	| "sales_log.sml"	|
        
    @skip_scenario
    Scenario Outline: Exporting data when there is no data (UC14)
    	Given There is no data when the user tries to export
    	And The data would be of type <dataType>
    	When The user chooses to export the data
    	Then The user is notified that there is no data to be exported
    	And No data is exported
        Examples:
        | dataType 		| fileName			|
        | "INGREDEIENT"	| "ingredients.xml" |
        | "RECIPE"		| "recipes.xml"		|
        | "MENU"		| "menu.xml"		|
        | "SUPPLIER"	| "suppliers.xml" 	|
        | "SALES_LOG"	| "sales_log.sml"	|
        
    # Scenarios related to generating sales reports
    
    @skip_scenario
    Scenario: Generating Sales Report (UC14)
    	Given The user wants to generate a sales report of the sales
    	When The usere chooses to generate a sales report
    	Then A sales report is generated in the foodstart directory 
    	And The file type is of a csv
    	And The file contains information on the recipes that have been sold for today
    	And Has their quantities that were sold today
