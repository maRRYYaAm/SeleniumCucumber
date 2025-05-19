@MasterRun
Feature: Complete end-to-end test

  @Order1
  Scenario: Home page navigation
    Given user is on the home page
    When user clicks on the Sign In link
    Then Sign In page should be displayed
    When user navigates back and clicks on the Create Account link
    Then Create Account page should be displayed

  @Order2
  Scenario: Register with valid and unique credentials
    Given user is on the registration page
    When user enters valid registration details
    And submits the registration form
    Then user should be redirected to account page
    And account page should show correct first name, last name, and email

  @Order3
  Scenario: Log in with valid credentials
    Given user is on the login page
    When user enters valid credentials
    And clicks the login button
    Then user should be logged in successfully

  @Order4
  Scenario: Search product by title
    Given user is on the product listing page
    When user searches for product by title
    Then searched product should be displayed

  @Order5
  Scenario: Sort products by lowest price
    Given user is on the product listing page
    When user selects sort by price: low to high
    Then products should be displayed in ascending price order

  @Order6
  Scenario: Sort products by highest price
    Given user is on the product listing page
    When user selects sort by price: high to low
    Then products should be displayed in descending price order

  @Order7
  Scenario: Add product to cart and verify details
    Given user is on the product listing page
    When user opens product "Radiant Tee" page
    And adds the product to the cart
    Then cart should have 1 item
    And cart total should be $22.00
    And cart should display product "Radiant Tee"


