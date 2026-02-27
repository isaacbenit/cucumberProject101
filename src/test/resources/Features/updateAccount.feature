@update
Feature: update customer information functionality

  As a registered customer
  I want to update my personal information (password)
  So that I can keep my profile information accurate and secure.

  Background:
    Given I am on the Account Page
    And I log in with valid credentials
      | username | isaacs     |
      | password | isaac!  |
  @SCRUM-49
  Scenario: Update my password using current invalid password
    When I update my password using an invalid password
      | firstName | lastName  | password        | newPassword  |
      | Irakoze   | isaac     | benit           |  12345       |
    Then I should see an error message

  @SCRUM-45
  Scenario: update my password
    When I update my password
      | firstName | lastName  | password        | newPassword  |
      | Irakoze   | isaac     | girl@1          | isaac!      |
    Then I should see a confirmation message







