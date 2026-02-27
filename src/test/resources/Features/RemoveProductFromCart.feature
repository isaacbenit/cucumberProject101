Feature: Remove product from cart

  Scenario: User removes a product from the shopping cart

    Given I have product in the cart
    When I click remove button
    Then the product should no longer be in the cart