Feature: Place an order
  As a customer
  I want to place order and provide my billing details
  so that I can be able to purchase a product


  Background: customer is on the store page
    Given I am on the store page
    And I add a "Blue Shoes" to the cart
  @SCRUM-56
  Scenario: I can checkOut so that i can place an order
    When I check out add my billing detail and place order
      | First name | Last name | Company name | Country/Region     | Street address     | City  | State | ZIP code | Email address      | Phone |
      | demo       | user      | MyCompany    | United States (US) | 6300 Spring Creek  | Plano | Texas | 75024    | askomdch@gmail.com | 1234567890 |
    Then the order should be placed successfully
  @SCRUM-62
  Scenario: Checkout fails when we use invalid data (email)
    When I check out add my billing detail and place order
      | First name | Last name | Company name | Country/Region     | Street address     | City  | State | ZIP code | Email address      | Phone      |
      | demo       | user      | MyCompany    | United States (US) | 6300 Spring Creek  | Plano | Texas | 0000    |        1            | 0788951952 |
    Then I should see an error message "Invalid billing email address"
