Feature: filter functionality
  As a user
  I want to filter products
  So that I can quickly find products I am interested to buy

  Background:
    Given I am on the store page

  @filterPrice
  Scenario: Filter products by different price ranges
    When I filter products within the price range
      | min_price | max_price |
      | 10        | 50        |
      | 50        | 100       |
      | 100       | 150       |
    Then I should see only products within the specified price range


  @SCRUM-34
  Scenario Outline: Filter products by category and verify product count
    When I select and click category "<category>" from the category filter
    Then I should see <count> products in the "<category>" category
    Examples:
      | category            | count |
      | Accessories         | 3     |
      | Men's Shirts        | 1     |
      | Purses And Handbags | 1     |
      | Women's Shoes       | 1     |
