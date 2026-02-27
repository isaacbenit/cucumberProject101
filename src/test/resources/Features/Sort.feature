@SortProduct
Feature: Product Sorting
  As a customer
  I want to sort products
  So that I can find products faster.

  @SortByPriceLowToHigh @SCRUM-15
  Scenario Outline: Sort by price low to high
    Given I am on the store page
    When I Sort by price "<Options>"
    Then The products should be sorted by price from "<Options>"
    Examples:
      | Options     |
      | high to low |
      | low to high |