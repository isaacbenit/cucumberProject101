@account
Feature: login functionality

  As a registered customer
  I want to login into the AskOmDch Website
  So that I can be able to access my saved information
  and be prevented from logging in when credentials are invalid


  Background:
    Given I am on the Account Page

  Scenario Outline: Login with invalid credentials
    When I log in with username "<username>" and password "<password>"
    Then I should see a login error message "<errorMessage>"

    Examples:
      | username | password  | errorMessage                                                                                                                    |
      | user2    | wrongPass | Error: The password you entered for the username user2 is incorrect. Lost your password?                                        |
      |          | user2!    | Error: Username is required.                                                                                                    |
      | user2    |           | Error: The password field is empty.                                                                                             |
      | unknown  | user2!    | Error: The username unknown is not registered on this site. If you are unsure of your username, try your email address instead. |

  Scenario: Login with valid credentials
    When I log in with valid credentials
      | username | umuhungu        |
      | password | user2!          |
    Then I should be redirected to the Dashboard
    And I should see a welcome message with "umuhungu"


