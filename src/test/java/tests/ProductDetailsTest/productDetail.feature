@postLogin
Feature: Product details page functionality

  @requiresLogin
  Scenario Outline: View product details
    Given I am logged in with valid credentials
    When I click on a product "<productName>" on the inventory page
    Then I should see the product details page
    And I should see the product name, description, price, and image
    And Click on view all items from slide menu
    Examples:
      | productName         |
      | Sauce Labs Backpack |
      |Sauce Labs Bike Light|

  Scenario Outline: Add product To Cart
    Given I am logged in with valid credentials
    When I click on a product "<productName>" on the inventory page
    Then I should see the product details page
    And I should see the product name, description, price, and image
    And Click on view all items from slide menu
    And Click on add to cart
    Examples:
      | productName         |
      | Sauce Labs Backpack |
      |Sauce Labs Bike Light1|

  Scenario Outline: Complete checkout with valid information
    When I click on the cart icon
    And I proceed to checkout
    And I enter "<fName>" as first name
    And I enter "<lName>" as last name
    And I enter "<zip>" as zip code
    And I continue to payment
    And I complete the purchase
    Then I should see the order confirmation message
    Examples:
      | fName|lName|zip|
      | firstName|lastName|zipCode|