Feature: Search and add product to the cart as guest and registered user.

  Background:
    Given user goes to "https://env-jam.portnov.com/"
    Then page title should be "Your store. Home page title"
    Given user is on Login page
    When user provides login credentials
    And clicks on login button

   Scenario: Search and add Product to cart as Registered User
#  Verify registered user is able to add product to cart and proceed to check out. This scenario doesn't go beyond checkout page
#  since the order confirmation is broken in the web application side.
    When user search for product "Nike Floral Roshe Customized Running Shoes"
    And  user clicks on "Search" button
    Then searchResult Page includes "Nike Floral Roshe Customized Running Shoes"
    Then click on the product "Nike Floral Roshe Customized Running Shoes"
    And user selects size, color and print for the product
    When User adds product to cart
    Then "The product has been added to your shopping cart" message appears on top of the page
    When user hoovers over shopping cart option
    And user clicks on "go to cart" button
    Then page title should be "Your store. Shopping Cart"
    And added product "Nike Floral Roshe Customized Running Shoes" should appear in shopping cart
    When user checks Terms of Service checkbox
    And user clicks on "Check out" button
    Then user is redirected to "Checkout page"


  Scenario: Verify Registered User is able to provide product review
#    Verify success message appears as registered user provides review for a product.
    When user search for product "Lego"
    And  user clicks on "Search" button
    Then searchResult Page includes "Lego"
    Then click on the product "Lego"
    And verify Add your review link is visible
    When user clicks on "Add your review" link
    Then user proceeds to "Product Reviews" page
    And user provides Review title , Review text and Rating for the product
    And user clicks on "Submit review" button
    Then "Product review is successfully added." message appears on Product Review page