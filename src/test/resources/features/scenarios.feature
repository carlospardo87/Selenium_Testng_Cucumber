Feature: To search testng in google

  @Regression
  Scenario: Testng Google
    Given I type "Arduino"
    When I click search button
    Then I clear search textbox

  @Regression
 Scenario: Junit Google
    Given I type "Kin+Carta"
    When I click search button
    Then I clear search text box


  @Regression
  Scenario: Junit Google
    Given I type "Nike"
    When I click search button
    Then I clear search text boxes
