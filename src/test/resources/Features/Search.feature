@searchProduct
  Feature: Product Search
    As a customer
    I want to search for products by name
    So that I can find what I'm looking for quickly

  @FullSearchResults @SCRUM-29
  Scenario: Search with full product name
    Given I am on the store page
    When I enter "Blue Shoes" into the search bar
    Then I should see results showing product with its names


@InvalidSearch @SCRUM-31
Scenario: Search with invalid data
  Given I am on the store page
  When I enter "###" into the search bar
  Then I should see error message: "No products were found matching your selection."