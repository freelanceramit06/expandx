Feature: Proforma Invoice functionality

  Scenario Outline: Verify Create Proforma Invoice functionality
    Given the user reads the quick filter value for "Submit" and stores it for future use
    And the user reads the quick filter value for "Cancelled" and stores it for future use
    And the user clicks on the "Create Proforma Invoice" button
    And the user selects "<customer>" value from drop down for "Select Customer" field
    And the user selects "<orderNumber>" value from drop down for "Select Order Number" field
    When the user clicks on Create button
    And the user switches to a new tab
    And the user select "<checkBoxLabel>" check box with value as "<checkBoxValue>"
    And the user enters "<invoiceValue1>" in the "<invoiceField1>" field on the PROFORMA INVOICE page
    And the user enters "<invoiceValue1>" in the "<invoiceField1>" field on the PROFORMA INVOICE page
    And the user enters "<invoiceValue2>" in the "<invoiceField2>" field on the PROFORMA INVOICE page
    And the user enters "<invoiceValue3>" in the "<invoiceField3>" field on the PROFORMA INVOICE page
    And the user enters "<invoiceValue4>" in the "<invoiceField4>" field on the PROFORMA INVOICE page
    And the user reads the text value for "Total Proforma Amount ($) [A%+B%+C]" field and stores it for future use
    And the user clicks on the "Submit" button
    Then the user should verify that Proforma Invoice is created successfully with "<customer>", "<orderNumber>", "Proforma Amount"
    And verify the "Submit" quick filter value is incremented by 1
    And the user clicks on the "Cancel" button
    And the user enter "Cancelling Proforma Invoice" in the textarea based on label "Reason"
    And the user clicks on the "Yes" button
    And the user should verify that Proforma Invoice is cancelled successfully with "is rejected for reason" message
    And the user clicks on the "OK" button
    And verify the "Cancelled" quick filter value is incremented by 1

    Examples:
      | customer                           | orderNumber                 | checkBoxLabel | checkBoxValue | invoiceField1 | invoiceValue1 | invoiceField2  | invoiceValue2 | invoiceField3  | invoiceValue3 | invoiceField4   | invoiceValue4 |
      | 2M INTEGRATION AND SAFETY CONTROLS | EPX/OA/MELTYR/2025-26/02160 | Without Tax   | Yes           | TCS           | 1.88          | Advance of A % | 8.00          | Advance of B % | 1.01          | RoundOff Amount | 10.99         |