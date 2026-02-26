@order
Feature: Order placement

  As a customer
  I want to place an order
  So that I can buy products

  Scenario: Place a simple order
    Given I have a product in the cart
    When I place the order with valid details
    Then I should see the order confirmation

