@account
Feature: login functionality

  As a registered customer
  I want to login into the AskOmDch Website
  So that I can be able to access my saved information
  and be prevented from logging in when credentials are invalid

  Background:
    Given I am on the Account Page

  Scenario: Login with valid credentials
    And I have registered a new user with a random suffix
    When I log in with the credentials I just registered
    Then I should be redirected to the Dashboard
    And I should see a welcome message for my account

  Scenario Outline: Login with invalid credentials
    When I log in with username "<username>" and password "<password>"
    Then I should see a login error message "<errorMessage>"

    Examples:
      | username | password   | errorMessage                                                                                                                         |
      | testuser | wrongPass  | Error: The username testuser is not registered on this site. If you are unsure of your username, try your email address instead.   |
      |          | Test123!   | Error: Username is required.                                                                                                         |
      | testuser |            | Error: The password field is empty.                                                                                                  |
      | unknown  | Test123!   | Error: The username unknown is not registered on this site. If you are unsure of your username, try your email address instead.    |
