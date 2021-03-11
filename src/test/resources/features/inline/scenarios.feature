Feature: Created scenarios inline

  @Regression
  Scenario: Looking for word Arduino
    Given I type "Arduino"
    When I click search button
    Then I clear search textbox

  @Regression
 Scenario: Looking for word Kin+Carta
    Given I type "Kin+Carta"
    When I click search button
    Then I clear search textbox


 @Regression
  Scenario: Looking for word Nike
    Given I type "Nike"
    When I click search button
    Then I clear search text box
