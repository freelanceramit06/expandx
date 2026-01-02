Feature: Login functionality

  Background:
    Given the user navigates to the login page URL

  Scenario Outline: Login with different credentials with 0
    When the user enters username "<username>"
    And the user enters password "<password>"
    And the user clicks on the "Login" button
    Then the login result should be "<result>"
    Given the user clicks on the "Accounts" button
    And the user clicks on the "Proforma Invoice" button

    Examples:
      | username                    | password | result  |
      | a.achlerkar@splendornet.com | !@QW12qw | success |