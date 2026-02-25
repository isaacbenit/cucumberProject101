Feature: Product Detail Page Functionality
  As a customer
  I want to view a product details
  So that I can understand the product before buying it.


    @view-product-detail
    Scenario: view product detail page
      Given I am on the store page
      When I click on any product on store page
      Then I should see all product information

