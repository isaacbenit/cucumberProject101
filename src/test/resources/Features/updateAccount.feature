Feature: update customer information functionality

  As a registered customer
  I want to update my personal information (password)
  So that I can keep my profile information accurate and secure.

  Background:
    Given I am on the Account Page
    And I have registered a new user with a random suffix
    And I log in with the credentials I just registered

  Scenario: update my password
    When I update my password
      | firstName | lastName | password | newPassword  |
      | Isaac     | Benit    | Test123! | NewTest123! |
    Then I should see a confirmation message

  Scenario: Update my password using current invalid password
    When I update my password using an invalid password
      | firstName | lastName | password  | newPassword  |
      | Isaac     | Benit    | wrongpass | NewTest123!  |
    Then I should see an error message
