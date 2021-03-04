Feature: create scenarios outline

  Scenario Outline: Looking for some word
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
