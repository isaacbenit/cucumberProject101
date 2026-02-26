@ValidRegistration
Feature: Customer Registration functionality

  As a new Customer
  I want to create an account
  So that I can log in and have a personalized experience

  Background:
    Given I am on the Account Page

  @invalidRegistration

  Scenario Outline: Register with invalid data
    When I register with invalid "<username>","<email>" and "<password>"
    And clicks the register button
    Then the user should see an error message "<errorMessage>"
    Examples:
      | username    | email                | password   | errorMessage                                                                    |
      | Testing Web | testing@Web.com      | testing123 | Error: An account is already registered with your email address. Please log in. |
      |             | user1234@test.com    | testing123 | Error: Please enter a valid account username.                                   |
      | user1       |                      | user1!     | Error: Please provide a valid email address.                                    |
      | user2       | user2@com            | user2!     | Error: Please provide a valid email address.                                    |
      | user3       | user3@example.com    |            | Error: Please enter an account password.                                        |


  Scenario Outline: Register with valid data
    When I register with username "<username>", email "<email>", and password "<password>"
    And clicks the register button
    Then the account is registered and I get welcome message with name "<username>"
    Examples:
      | username | email              | password        |
      | isaac    | isaac@gmail.com    | isaacaskomdch   |