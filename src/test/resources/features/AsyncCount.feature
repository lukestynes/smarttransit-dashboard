Feature: Asynchronous Count
  Scenario: COUNT rows in database
    Given I have loaded the query_test_data.csv file
    When I query for the number of results returned by an empty search
    Then I should find that my query returns $10255 results


  Scenario: COUNT rows in database with specific search
    Given I have loaded the query_test_data.csv file
    When I query for the number of results returned by a search where buses involved
    Then I should find that my query returns $150 results

