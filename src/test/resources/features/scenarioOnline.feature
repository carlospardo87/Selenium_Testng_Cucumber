Feature: To search cucumber in google

  Scenario Outline: Cucumber Google
      Given I type "<type>"
      When I click search button
      Then I clear search textbox

    Examples:
      | type |
      |cucumber|
      |Selenium|
      |TestNg|
      |Java8|
      |Protractor|
      |Maven|
