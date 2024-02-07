Feature: General Searching
  Scenario Outline: Keyword ANY Search
    Given I have a fresh basic search
    When I enter <keywords> as a keyword phrase
    And the search is matching any terms
    Then if there is a result, its string fields must contain any of the words in <keywords>
  Examples:
    |keywords|
    |"ROAD AVENUE"|
    |"FATAL AUCKLAND"|


  Scenario Outline: Keyword ALL Search
    Given I have a fresh basic search
    When I enter <keywords> as a keyword phrase
    And the search is matching all terms
    Then if there is a result, its string fields must contain all of the words in <keywords>
  Examples:
    |keywords|
    |"ROAD AVENUE"|
    |"FATAL AUCKLAND"|

  Scenario Outline: Discrete Range Search
    Given I have a fresh basic search
    When I enable the range <field> with values <lowerBound> and <upperBound>
    Then if there is a result, its field <field> is between <lowerBound> and <upperBound>
  Examples:
    |field|lowerBound|upperBound|
    |"Year"|2004     |2008      |
    |"Speed Limit"|60|90        |
    |"Advised Speed"|50|90      |
    |"Temporary Speed Limit"|30|60|
    |"Lanes"     |2 |5 |

  Scenario Outline: Standard CheckComboBox Search
    Given I have a fresh basic search
    When I select <selection> from the <field> box
    Then if there is a result, its field <field> must match one of the <selection> values
  Examples:
    |selection|field|
    |"Fatal Crash,Minor Crash"|"Severity"|
    |"Auckland,Canterbury"    |"Region"  |

  Scenario Outline: ANY CheckComboBox Search
    Given I have a fresh basic search
    When I select <selection> from the <field> box with Any enabled
    Then if there is a result, its field <field> must have at least one <selection> present
  Examples:
    |selection|field|
    |"Bicycles,Buses"|"Participants"|
    |"Fence Hits,Kerb Hits"|"Collisions"|

  Scenario Outline: ALL CheckComboBox Search
    Given I have a fresh basic search
    When I select <selection> from the <field> box with All enabled
    Then if there is a result, its field <field> must have all <selection> present
  Examples:
    |selection|field|
    |"Bicycles,Buses"|"Participants"|
    |"Fence Hits,Kerb Hits"|"Collisions"|