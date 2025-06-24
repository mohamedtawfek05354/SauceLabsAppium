Feature: check for the home screen

  Scenario Outline: login with invalid credentials
    Given I am on the Saucedemo login page
    When I enter the username "<username>" and the password "<password>"
    And I click on the login button
    Then I should see pop up Alert message

    Examples:
      | username       | password      |
      | standard | secret_sauce  |

  Scenario Outline: Successful login with valid credentials
    Given I am on the Saucedemo login page
    When I enter the username "<username>" and the password "<password>"
    And I click on the login button
    Then I should be redirected to the inventory page

    Examples:
      | username       | password      |
      | standard_user | secret_sauce  |
