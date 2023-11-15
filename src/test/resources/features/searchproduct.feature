@searchproduct
Feature: Search and add product to the cart as guest and registered user.

  Background:
    Given user goes to "https://env-jam.portnov.com/"
    Then page title should be "Your store. Home page title"

  @searchproduct1
  Scenario: Search and add Product to cart as Guest

    When user search for product "Lego"
    And  user clicks on "Search" button
    Then searchResult Page includes "Lego"
    Then click on the product "Lego"
    And user adds quantity of 4 for the product
    When User adds product "Lego" to cart
    Then "The product has been added to your shopping cart" message appears on top of the page
    When user hoovers over shopping cart option
    And user clicks on "go to cart" button
    Then page title should be "Your store. Shopping Cart"
    Then the shopping cart should contain 1 product
    And total price should be "$40.00"
    When user checks Terms of Service checkbox
    And user clicks on "Check out" button
    Then user is redirected to "Login page"
    And user clicks on "Checkout as Guest" button
    Then user is redirected to "Checkout page"

  @searchproduct2
   Scenario: Search and add Product to cart as Registered User
    Given user is on Login page
    When user provides login credentials
    And clicks on login button
    Then