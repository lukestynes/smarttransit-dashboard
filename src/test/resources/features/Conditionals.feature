Feature: Conditional Searching
  Scenario Outline: EQUAL Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should be the value <value>
  Examples:
    |field|operator|value|
    |"ID"|"is"|"80"|
    |"Location 1"|"is"|"SH 7"|
    |"Location 2"|"is"|"SH 16"|
    |"Severity"|"is"|"Minor Crash"|
    |"Year"|"is"|"2022"|
    |"Region"|"is"|"Auckland Region"|
    |"Speed Limit"|"is"|"100"|
    |"Precipitation"|"is"|"Fine"|
    |"Frost or Wind"|"is"|"Strong Wind"|
    |"Advised Speed"|"is"|"85"|
    |"Holiday"|"is"|"Labour Weekend"|
    |"Light"|"is"|"Twilight"|
    |"Street Lighting"|"is"|"On"|
    |"Temporary Speed Limit"|"is"|"80"|
    |"Territorial Local Authority"|"is"|"Wairoa District"|
    |"Traffic Control"|"is"|"Stop"|
    |"Crash Direction"|"is"|"North"|
    |"Financial Year"|"is"|"2000/2001"|
    |"State Highway"|"is"|"Yes"|
    |"Flat/Hill"|"is"|"Flat"|
    |"Lanes"|"is"|"3"|
    |"Road Character"|"is"|"Bridge"|
    |"Road Lanes"|"is"|"2-way"|
    |"Road Surface"|"is"|"Sealed"|
    |"Urban"|"is"|"Urban"|
    |"Deaths"|"is"|"1"|
    |"Minor Injuries"|"is"|"1"|
    |"Serious Injuries"|"is"|"1"|
    |"Bicycles"|"is"|"1"|
    |"Buses"|"is"|"1"|
    |"Cars/Station Wagons"|"is"|"1"|
    |"Mopeds"|"is"|"1"|
    |"Motorcycles"|"is"|"1"|
    |"Other Vehicles"|"is"|"1"|
    |"Pedestrians"|"is"|"1"|
    |"School Buses"|"is"|"1"|
    |"SUVs"|"is"|"1"|
    |"Taxis"|"is"|"1"|
    |"Trucks"|"is"|"1"|
    |"Unknown Vehicles"|"is"|"1"|
    |"Bridge Hits"|"is"|"1"|
    |"Cliff/Bank Hits"|"is"|"1"|
    |"Debris Hits"|"is"|"1"|
    |"Ditch Hits"|"is"|"1"|
    |"Fence Hits"|"is"|"1"|
    |"Guard Rail Hits"|"is"|"1"|
    |"Building Hits"|"is"|"1"|
    |"Kerb Hits"|"is"|"1"|
    |"Objects Thrown/Dropped"|"is"|"1"|
    |"Other Objects Struck"|"is"|"1"|
    |"Embankment Hits"|"is"|"1"|
    |"Parked Vehicle Hits"|"is"|"1"|
    |"Phone Box, etc. Hits"|"is"|"1"|
    |"Post/Pole Hits"|"is"|"1"|
    |"Road Works Hits"|"is"|"1"|
    |"Slip/Flood Hits"|"is"|"1"|
    |"Stray Animal Hits"|"is"|"1"|
    |"Traffic Island Hits"|"is"|"1"|
    |"Traffic Sign Hits"|"is"|"1"|
    |"Train Hits"|"is"|"1"|
    |"Tree Hits"|"is"|"1"|
    |"Vehicle Hits"|"is"|"1"|
    |"Water Hits"|"is"|"1"|

    
  Scenario Outline: NOTEQUAL Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should not be the value <value>
  Examples:
    |field|operator|value|
    |"ID"|"is not"|"80"|
    |"Location 1"|"is not"|"SH 7"|
    |"Location 2"|"is not"|"SH 16"|
    |"Severity"|"is not"|"Minor Crash"|
    |"Year"|"is not"|"2022"|
    |"Region"|"is not"|"Auckland Region"|
    |"Speed Limit"|"is not"|"100"|
    |"Precipitation"|"is not"|"Fine"|
    |"Frost or Wind"|"is not"|"Strong Wind"|
    |"Advised Speed"|"is not"|"85"|
    |"Holiday"|"is not"|"Labour Weekend"|
    |"Light"|"is not"|"Twilight"|
    |"Street Lighting"|"is not"|"On"|
    |"Temporary Speed Limit"|"is not"|"80"|
    |"Territorial Local Authority"|"is not"|"Wairoa District"|
    |"Traffic Control"|"is not"|"Stop"|
    |"Crash Direction"|"is not"|"North"|
    |"Financial Year"|"is not"|"2000/2001"|
    |"State Highway"|"is not"|"Yes"|
    |"Flat/Hill"|"is not"|"Flat"|
    |"Lanes"|"is not"|"3"|
    |"Road Character"|"is not"|"Bridge"|
    |"Road Lanes"|"is not"|"2-way"|
    |"Road Surface"|"is not"|"Sealed"|
    |"Urban"|"is not"|"Urban"|
    |"Deaths"|"is not"|"1"|
    |"Minor Injuries"|"is not"|"1"|
    |"Serious Injuries"|"is not"|"1"|
    |"Bicycles"|"is not"|"1"|
    |"Buses"|"is not"|"1"|
    |"Cars/Station Wagons"|"is not"|"1"|
    |"Mopeds"|"is not"|"1"|
    |"Motorcycles"|"is not"|"1"|
    |"Other Vehicles"|"is not"|"1"|
    |"Pedestrians"|"is not"|"1"|
    |"School Buses"|"is not"|"1"|
    |"SUVs"|"is not"|"1"|
    |"Taxis"|"is not"|"1"|
    |"Trucks"|"is not"|"1"|
    |"Unknown Vehicles"|"is not"|"1"|
    |"Bridge Hits"|"is not"|"1"|
    |"Cliff/Bank Hits"|"is not"|"1"|
    |"Debris Hits"|"is not"|"1"|
    |"Ditch Hits"|"is not"|"1"|
    |"Fence Hits"|"is not"|"1"|
    |"Guard Rail Hits"|"is not"|"1"|
    |"Building Hits"|"is not"|"1"|
    |"Kerb Hits"|"is not"|"1"|
    |"Objects Thrown/Dropped"|"is not"|"1"|
    |"Other Objects Struck"|"is not"|"1"|
    |"Embankment Hits"|"is not"|"1"|
    |"Parked Vehicle Hits"|"is not"|"1"|
    |"Phone Box, etc. Hits"|"is not"|"1"|
    |"Post/Pole Hits"|"is not"|"1"|
    |"Road Works Hits"|"is not"|"1"|
    |"Slip/Flood Hits"|"is not"|"1"|
    |"Stray Animal Hits"|"is not"|"1"|
    |"Traffic Island Hits"|"is not"|"1"|
    |"Traffic Sign Hits"|"is not"|"1"|
    |"Train Hits"|"is not"|"1"|
    |"Tree Hits"|"is not"|"1"|
    |"Vehicle Hits"|"is not"|"1"|
    |"Water Hits"|"is not"|"1"|

    
  Scenario Outline: GREATERTHAN Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should be greater than <value>
  Examples:
    |field|operator|value|
    |"ID"|"greater than"|"80"|
    |"Year"|"greater than"|"2022"|
    |"Speed Limit"|"greater than"|"100"|
    |"Advised Speed"|"greater than"|"85"|
    |"Temporary Speed Limit"|"greater than"|"80"|
    |"Lanes"|"greater than"|"3"|
    |"Deaths"|"greater than"|"1"|
    |"Minor Injuries"|"greater than"|"1"|
    |"Serious Injuries"|"greater than"|"1"|
    |"Bicycles"|"greater than"|"1"|
    |"Buses"|"greater than"|"1"|
    |"Cars/Station Wagons"|"greater than"|"1"|
    |"Mopeds"|"greater than"|"1"|
    |"Motorcycles"|"greater than"|"1"|
    |"Other Vehicles"|"greater than"|"1"|
    |"Pedestrians"|"greater than"|"1"|
    |"School Buses"|"greater than"|"1"|
    |"SUVs"|"greater than"|"1"|
    |"Taxis"|"greater than"|"1"|
    |"Trucks"|"greater than"|"1"|
    |"Unknown Vehicles"|"greater than"|"1"|
    |"Bridge Hits"|"greater than"|"1"|
    |"Cliff/Bank Hits"|"greater than"|"1"|
    |"Debris Hits"|"greater than"|"1"|
    |"Ditch Hits"|"greater than"|"1"|
    |"Fence Hits"|"greater than"|"1"|
    |"Guard Rail Hits"|"greater than"|"1"|
    |"Building Hits"|"greater than"|"1"|
    |"Kerb Hits"|"greater than"|"1"|
    |"Objects Thrown/Dropped"|"greater than"|"1"|
    |"Other Objects Struck"|"greater than"|"1"|
    |"Embankment Hits"|"greater than"|"1"|
    |"Parked Vehicle Hits"|"greater than"|"1"|
    |"Phone Box, etc. Hits"|"greater than"|"1"|
    |"Post/Pole Hits"|"greater than"|"1"|
    |"Road Works Hits"|"greater than"|"1"|
    |"Slip/Flood Hits"|"greater than"|"1"|
    |"Stray Animal Hits"|"greater than"|"1"|
    |"Traffic Island Hits"|"greater than"|"1"|
    |"Traffic Sign Hits"|"greater than"|"1"|
    |"Train Hits"|"greater than"|"1"|
    |"Tree Hits"|"greater than"|"1"|
    |"Vehicle Hits"|"greater than"|"1"|
    |"Water Hits"|"greater than"|"1"|

    
  Scenario Outline: GREATERTHANOREQUAL Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should be at least <value>
  Examples:
    |field|operator|value|
    |"ID"|"at least"|"80"|
    |"Year"|"at least"|"2022"|
    |"Speed Limit"|"at least"|"100"|
    |"Advised Speed"|"at least"|"85"|
    |"Temporary Speed Limit"|"at least"|"80"|
    |"Lanes"|"at least"|"3"|
    |"Deaths"|"at least"|"1"|
    |"Minor Injuries"|"at least"|"1"|
    |"Serious Injuries"|"at least"|"1"|
    |"Bicycles"|"at least"|"1"|
    |"Buses"|"at least"|"1"|
    |"Cars/Station Wagons"|"at least"|"1"|
    |"Mopeds"|"at least"|"1"|
    |"Motorcycles"|"at least"|"1"|
    |"Other Vehicles"|"at least"|"1"|
    |"Pedestrians"|"at least"|"1"|
    |"School Buses"|"at least"|"1"|
    |"SUVs"|"at least"|"1"|
    |"Taxis"|"at least"|"1"|
    |"Trucks"|"at least"|"1"|
    |"Unknown Vehicles"|"at least"|"1"|
    |"Bridge Hits"|"at least"|"1"|
    |"Cliff/Bank Hits"|"at least"|"1"|
    |"Debris Hits"|"at least"|"1"|
    |"Ditch Hits"|"at least"|"1"|
    |"Fence Hits"|"at least"|"1"|
    |"Guard Rail Hits"|"at least"|"1"|
    |"Building Hits"|"at least"|"1"|
    |"Kerb Hits"|"at least"|"1"|
    |"Objects Thrown/Dropped"|"at least"|"1"|
    |"Other Objects Struck"|"at least"|"1"|
    |"Embankment Hits"|"at least"|"1"|
    |"Parked Vehicle Hits"|"at least"|"1"|
    |"Phone Box, etc. Hits"|"at least"|"1"|
    |"Post/Pole Hits"|"at least"|"1"|
    |"Road Works Hits"|"at least"|"1"|
    |"Slip/Flood Hits"|"at least"|"1"|
    |"Stray Animal Hits"|"at least"|"1"|
    |"Traffic Island Hits"|"at least"|"1"|
    |"Traffic Sign Hits"|"at least"|"1"|
    |"Train Hits"|"at least"|"1"|
    |"Tree Hits"|"at least"|"1"|
    |"Vehicle Hits"|"at least"|"1"|
    |"Water Hits"|"at least"|"1"|

    
  Scenario Outline: LESSTHAN Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should be less than <value>
  Examples:
    |field|operator|value|
    |"ID"|"less than"|"80"|
    |"Year"|"less than"|"2022"|
    |"Speed Limit"|"less than"|"100"|
    |"Advised Speed"|"less than"|"85"|
    |"Temporary Speed Limit"|"less than"|"80"|
    |"Lanes"|"less than"|"3"|
    |"Deaths"|"less than"|"1"|
    |"Minor Injuries"|"less than"|"1"|
    |"Serious Injuries"|"less than"|"1"|
    |"Bicycles"|"less than"|"1"|
    |"Buses"|"less than"|"1"|
    |"Cars/Station Wagons"|"less than"|"1"|
    |"Mopeds"|"less than"|"1"|
    |"Motorcycles"|"less than"|"1"|
    |"Other Vehicles"|"less than"|"1"|
    |"Pedestrians"|"less than"|"1"|
    |"School Buses"|"less than"|"1"|
    |"SUVs"|"less than"|"1"|
    |"Taxis"|"less than"|"1"|
    |"Trucks"|"less than"|"1"|
    |"Unknown Vehicles"|"less than"|"1"|
    |"Bridge Hits"|"less than"|"1"|
    |"Cliff/Bank Hits"|"less than"|"1"|
    |"Debris Hits"|"less than"|"1"|
    |"Ditch Hits"|"less than"|"1"|
    |"Fence Hits"|"less than"|"1"|
    |"Guard Rail Hits"|"less than"|"1"|
    |"Building Hits"|"less than"|"1"|
    |"Kerb Hits"|"less than"|"1"|
    |"Objects Thrown/Dropped"|"less than"|"1"|
    |"Other Objects Struck"|"less than"|"1"|
    |"Embankment Hits"|"less than"|"1"|
    |"Parked Vehicle Hits"|"less than"|"1"|
    |"Phone Box, etc. Hits"|"less than"|"1"|
    |"Post/Pole Hits"|"less than"|"1"|
    |"Road Works Hits"|"less than"|"1"|
    |"Slip/Flood Hits"|"less than"|"1"|
    |"Stray Animal Hits"|"less than"|"1"|
    |"Traffic Island Hits"|"less than"|"1"|
    |"Traffic Sign Hits"|"less than"|"1"|
    |"Train Hits"|"less than"|"1"|
    |"Tree Hits"|"less than"|"1"|
    |"Vehicle Hits"|"less than"|"1"|
    |"Water Hits"|"less than"|"1"|

    
  Scenario Outline: LESSTHANOREQUAL Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should be at most <value>
  Examples:
    |field|operator|value|
    |"ID"|"at most"|"80"|
    |"Year"|"at most"|"2022"|
    |"Speed Limit"|"at most"|"100"|
    |"Advised Speed"|"at most"|"85"|
    |"Temporary Speed Limit"|"at most"|"80"|
    |"Lanes"|"at most"|"3"|
    |"Deaths"|"at most"|"1"|
    |"Minor Injuries"|"at most"|"1"|
    |"Serious Injuries"|"at most"|"1"|
    |"Bicycles"|"at most"|"1"|
    |"Buses"|"at most"|"1"|
    |"Cars/Station Wagons"|"at most"|"1"|
    |"Mopeds"|"at most"|"1"|
    |"Motorcycles"|"at most"|"1"|
    |"Other Vehicles"|"at most"|"1"|
    |"Pedestrians"|"at most"|"1"|
    |"School Buses"|"at most"|"1"|
    |"SUVs"|"at most"|"1"|
    |"Taxis"|"at most"|"1"|
    |"Trucks"|"at most"|"1"|
    |"Unknown Vehicles"|"at most"|"1"|
    |"Bridge Hits"|"at most"|"1"|
    |"Cliff/Bank Hits"|"at most"|"1"|
    |"Debris Hits"|"at most"|"1"|
    |"Ditch Hits"|"at most"|"1"|
    |"Fence Hits"|"at most"|"1"|
    |"Guard Rail Hits"|"at most"|"1"|
    |"Building Hits"|"at most"|"1"|
    |"Kerb Hits"|"at most"|"1"|
    |"Objects Thrown/Dropped"|"at most"|"1"|
    |"Other Objects Struck"|"at most"|"1"|
    |"Embankment Hits"|"at most"|"1"|
    |"Parked Vehicle Hits"|"at most"|"1"|
    |"Phone Box, etc. Hits"|"at most"|"1"|
    |"Post/Pole Hits"|"at most"|"1"|
    |"Road Works Hits"|"at most"|"1"|
    |"Slip/Flood Hits"|"at most"|"1"|
    |"Stray Animal Hits"|"at most"|"1"|
    |"Traffic Island Hits"|"at most"|"1"|
    |"Traffic Sign Hits"|"at most"|"1"|
    |"Train Hits"|"at most"|"1"|
    |"Tree Hits"|"at most"|"1"|
    |"Vehicle Hits"|"at most"|"1"|
    |"Water Hits"|"at most"|"1"|

    
  Scenario Outline: CONTAINS Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should contain <value>
  Examples:
    |field|operator|value|
    |"ID"|"contains"|"8"|
    |"Location 1"|"contains"|"SH"|
    |"Location 2"|"contains"|"SH"|
    |"Severity"|"contains"|"Mino"|
    |"Year"|"contains"|"202"|
    |"Region"|"contains"|"Auckla"|
    |"Speed Limit"|"contains"|"1"|
    |"Precipitation"|"contains"|"Fi"|
    |"Frost or Wind"|"contains"|"nd"|
    |"Advised Speed"|"contains"|"8"|
    |"Holiday"|"contains"|"Lab"|
    |"Light"|"contains"|"Twi"|
    |"Street Lighting"|"contains"|"f"|
    |"Temporary Speed Limit"|"contains"|"8"|
    |"Territorial Local Authority"|"contains"|"Wairo"|
    |"Traffic Control"|"contains"|"Sto"|
    |"Crash Direction"|"contains"|"Nor"|
    |"Financial Year"|"contains"|"001/"|
    |"State Highway"|"contains"|"Y"|
    |"Flat/Hill"|"contains"|"at"|
    |"Lanes"|"contains"|"3"|
    |"Road Character"|"contains"|"Br"|
    |"Road Lanes"|"contains"|"2"|
    |"Urban"|"contains"|"Urb"|
    |"Deaths"|"contains"|"1"|
    |"Minor Injuries"|"contains"|"1"|
    |"Serious Injuries"|"contains"|"1"|
    |"Bicycles"|"contains"|"1"|
    |"Buses"|"contains"|"1"|
    |"Cars/Station Wagons"|"contains"|"1"|
    |"Mopeds"|"contains"|"1"|
    |"Motorcycles"|"contains"|"1"|
    |"Other Vehicles"|"contains"|"1"|
    |"Pedestrians"|"contains"|"1"|
    |"School Buses"|"contains"|"1"|
    |"SUVs"|"contains"|"1"|
    |"Taxis"|"contains"|"1"|
    |"Trucks"|"contains"|"1"|
    |"Unknown Vehicles"|"contains"|"1"|
    |"Bridge Hits"|"contains"|"1"|
    |"Cliff/Bank Hits"|"contains"|"1"|
    |"Debris Hits"|"contains"|"1"|
    |"Ditch Hits"|"contains"|"1"|
    |"Fence Hits"|"contains"|"1"|
    |"Guard Rail Hits"|"contains"|"1"|
    |"Building Hits"|"contains"|"1"|
    |"Kerb Hits"|"contains"|"1"|
    |"Objects Thrown/Dropped"|"contains"|"1"|
    |"Other Objects Struck"|"contains"|"1"|
    |"Embankment Hits"|"contains"|"1"|
    |"Parked Vehicle Hits"|"contains"|"1"|
    |"Phone Box, etc. Hits"|"contains"|"1"|
    |"Post/Pole Hits"|"contains"|"1"|
    |"Road Works Hits"|"contains"|"1"|
    |"Slip/Flood Hits"|"contains"|"1"|
    |"Stray Animal Hits"|"contains"|"1"|
    |"Traffic Island Hits"|"contains"|"1"|
    |"Traffic Sign Hits"|"contains"|"1"|
    |"Train Hits"|"contains"|"1"|
    |"Tree Hits"|"contains"|"1"|
    |"Vehicle Hits"|"contains"|"1"|
    |"Water Hits"|"contains"|"1"|
    
    
  Scenario Outline: NOTLIKE Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should not contain <value>
  Examples:
    |field|operator|value|
    |"ID"|"does not contain"|"8"|
    |"Location 1"|"does not contain"|"SH"|
    |"Location 2"|"does not contain"|"SH"|
    |"Severity"|"does not contain"|"Mino"|
    |"Year"|"does not contain"|"202"|
    |"Region"|"does not contain"|"Auckla"|
    |"Speed Limit"|"does not contain"|"1"|
    |"Precipitation"|"does not contain"|"Fi"|
    |"Frost or Wind"|"does not contain"|"nd"|
    |"Advised Speed"|"does not contain"|"8"|
    |"Holiday"|"does not contain"|"Lab"|
    |"Light"|"does not contain"|"Twi"|
    |"Street Lighting"|"does not contain"|"f"|
    |"Temporary Speed Limit"|"does not contain"|"8"|
    |"Territorial Local Authority"|"does not contain"|"Wairo"|
    |"Traffic Control"|"does not contain"|"Sto"|
    |"Crash Direction"|"does not contain"|"Nor"|
    |"Financial Year"|"does not contain"|"001/"|
    |"State Highway"|"does not contain"|"Y"|
    |"Flat/Hill"|"does not contain"|"at"|
    |"Lanes"|"does not contain"|"3"|
    |"Road Character"|"does not contain"|"Br"|
    |"Road Lanes"|"does not contain"|"2"|
    |"Road Surface"|"does not contain"|"Seal"|
    |"Urban"|"does not contain"|"Urb"|
    |"Deaths"|"does not contain"|"1"|
    |"Minor Injuries"|"does not contain"|"1"|
    |"Serious Injuries"|"does not contain"|"1"|
    |"Bicycles"|"does not contain"|"1"|
    |"Buses"|"does not contain"|"1"|
    |"Cars/Station Wagons"|"does not contain"|"1"|
    |"Mopeds"|"does not contain"|"1"|
    |"Motorcycles"|"does not contain"|"1"|
    |"Other Vehicles"|"does not contain"|"1"|
    |"Pedestrians"|"does not contain"|"1"|
    |"School Buses"|"does not contain"|"1"|
    |"SUVs"|"does not contain"|"1"|
    |"Taxis"|"does not contain"|"1"|
    |"Trucks"|"does not contain"|"1"|
    |"Unknown Vehicles"|"does not contain"|"1"|
    |"Bridge Hits"|"does not contain"|"1"|
    |"Cliff/Bank Hits"|"does not contain"|"1"|
    |"Debris Hits"|"does not contain"|"1"|
    |"Ditch Hits"|"does not contain"|"1"|
    |"Fence Hits"|"does not contain"|"1"|
    |"Guard Rail Hits"|"does not contain"|"1"|
    |"Building Hits"|"does not contain"|"1"|
    |"Kerb Hits"|"does not contain"|"1"|
    |"Objects Thrown/Dropped"|"does not contain"|"1"|
    |"Other Objects Struck"|"does not contain"|"1"|
    |"Embankment Hits"|"does not contain"|"1"|
    |"Parked Vehicle Hits"|"does not contain"|"1"|
    |"Phone Box, etc. Hits"|"does not contain"|"1"|
    |"Post/Pole Hits"|"does not contain"|"1"|
    |"Road Works Hits"|"does not contain"|"1"|
    |"Slip/Flood Hits"|"does not contain"|"1"|
    |"Stray Animal Hits"|"does not contain"|"1"|
    |"Traffic Island Hits"|"does not contain"|"1"|
    |"Traffic Sign Hits"|"does not contain"|"1"|
    |"Train Hits"|"does not contain"|"1"|
    |"Tree Hits"|"does not contain"|"1"|
    |"Vehicle Hits"|"does not contain"|"1"|
    |"Water Hits"|"does not contain"|"1"|
    

  Scenario Outline: BETWEEN Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and values <value1> and <value2>
    Then if there is a result, its field <field> should be between <value1> and <value2>
  Examples:
    |field|operator|value1|value2|
    |"ID"|"between"|"80"|"320"   |
    |"Year"|"between"|"2020"|"2022"|
    |"Speed Limit"|"between"|"70"|"90"|
    |"Advised Speed"|"between"|"60"|"85"|
    |"Temporary Speed Limit"|"between"|"60"|"80"|
    |"Lanes"|"between"|"3"|"5"                  |
    |"Deaths"|"between"|"1"|"2"                 |
    |"Minor Injuries"|"between"|"1"|"2"         |
    |"Serious Injuries"|"between"|"1"|"2"         |
    |"Bicycles"|"between"|"1"|"2"         |
    |"Buses"|"between"|"1"|"2"         |
    |"Cars/Station Wagons"|"between"|"1"|"2"         |
    |"Mopeds"|"between"|"1"|"2"         |
    |"Motorcycles"|"between"|"1"|"2"         |
    |"Other Vehicles"|"between"|"1"|"2"         |
    |"Pedestrians"|"between"|"1"|"2"         |
    |"School Buses"|"between"|"1"|"2"         |
    |"SUVs"|"between"|"1"|"2"         |
    |"Taxis"|"between"|"1"|"2"         |
    |"Trucks"|"between"|"1"|"2"         |
    |"Unknown Vehicles"|"between"|"1"|"2"         |
    |"Bridge Hits"|"between"|"1"|"2"         |
    |"Cliff/Bank Hits"|"between"|"1"|"2"         |
    |"Debris Hits"|"between"|"1"|"2"         |
    |"Ditch Hits"|"between"|"1"|"2"         |
    |"Fence Hits"|"between"|"1"|"2"         |
    |"Guard Rail Hits"|"between"|"1"|"2"         |
    |"Building Hits"|"between"|"1"|"2"         |
    |"Kerb Hits"|"between"|"1"|"2"         |
    |"Objects Thrown/Dropped"|"between"|"1"|"2"         |
    |"Other Objects Struck"|"between"|"1"|"2"         |
    |"Embankment Hits"|"between"|"1"|"2"         |
    |"Parked Vehicle Hits"|"between"|"1"|"2"         |
    |"Phone Box, etc. Hits"|"between"|"1"|"2"         |
    |"Post/Pole Hits"|"between"|"1"|"2"         |
    |"Road Works Hits"|"between"|"1"|"2"         |
    |"Slip/Flood Hits"|"between"|"1"|"2"         |
    |"Stray Animal Hits"|"between"|"1"|"2"         |
    |"Traffic Island Hits"|"between"|"1"|"2"         |
    |"Traffic Sign Hits"|"between"|"1"|"2"         |
    |"Train Hits"|"between"|"1"|"2"         |
    |"Tree Hits"|"between"|"1"|"2"         |
    |"Vehicle Hits"|"between"|"1"|"2"         |
    |"Water Hits"|"between"|"1"|"2"         |

  Scenario Outline: NOTLIKE Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should not contain <value>
  Examples:
    |field|operator|value|
    |"ID"|"does not contain"|"8"|
    |"Location 1"|"does not contain"|"SH"|
    |"Location 2"|"does not contain"|"SH"|
    |"Severity"|"does not contain"|"Mino"|
    |"Year"|"does not contain"|"202"|
    |"Region"|"does not contain"|"Auckla"|
    |"Speed Limit"|"does not contain"|"1"|
    |"Precipitation"|"does not contain"|"Fi"|
    |"Frost or Wind"|"does not contain"|"nd"|
    |"Advised Speed"|"does not contain"|"8"|
    |"Holiday"|"does not contain"|"Lab"|
    |"Light"|"does not contain"|"Twi"|
    |"Street Lighting"|"does not contain"|"f"|
    |"Temporary Speed Limit"|"does not contain"|"8"|
    |"Territorial Local Authority"|"does not contain"|"Wairo"|
    |"Traffic Control"|"does not contain"|"Sto"|
    |"Crash Direction"|"does not contain"|"Nor"|
    |"Financial Year"|"does not contain"|"001/"|
    |"State Highway"|"does not contain"|"Y"|
    |"Flat/Hill"|"does not contain"|"at"|
    |"Lanes"|"does not contain"|"3"|
    |"Road Character"|"does not contain"|"Br"|
    |"Road Lanes"|"does not contain"|"2"|
    |"Road Surface"|"does not contain"|"Seal"|
    |"Urban"|"does not contain"|"Urb"|
    |"Deaths"|"does not contain"|"1"|
    |"Minor Injuries"|"does not contain"|"1"|
    |"Serious Injuries"|"does not contain"|"1"|
    |"Bicycles"|"does not contain"|"1"|
    |"Buses"|"does not contain"|"1"|
    |"Cars/Station Wagons"|"does not contain"|"1"|
    |"Mopeds"|"does not contain"|"1"|
    |"Motorcycles"|"does not contain"|"1"|
    |"Other Vehicles"|"does not contain"|"1"|
    |"Pedestrians"|"does not contain"|"1"|
    |"School Buses"|"does not contain"|"1"|
    |"SUVs"|"does not contain"|"1"|
    |"Taxis"|"does not contain"|"1"|
    |"Trucks"|"does not contain"|"1"|
    |"Unknown Vehicles"|"does not contain"|"1"|
    |"Bridge Hits"|"does not contain"|"1"|
    |"Cliff/Bank Hits"|"does not contain"|"1"|
    |"Debris Hits"|"does not contain"|"1"|
    |"Ditch Hits"|"does not contain"|"1"|
    |"Fence Hits"|"does not contain"|"1"|
    |"Guard Rail Hits"|"does not contain"|"1"|
    |"Building Hits"|"does not contain"|"1"|
    |"Kerb Hits"|"does not contain"|"1"|
    |"Objects Thrown/Dropped"|"does not contain"|"1"|
    |"Other Objects Struck"|"does not contain"|"1"|
    |"Embankment Hits"|"does not contain"|"1"|
    |"Parked Vehicle Hits"|"does not contain"|"1"|
    |"Phone Box, etc. Hits"|"does not contain"|"1"|
    |"Post/Pole Hits"|"does not contain"|"1"|
    |"Road Works Hits"|"does not contain"|"1"|
    |"Slip/Flood Hits"|"does not contain"|"1"|
    |"Stray Animal Hits"|"does not contain"|"1"|
    |"Traffic Island Hits"|"does not contain"|"1"|
    |"Traffic Sign Hits"|"does not contain"|"1"|
    |"Train Hits"|"does not contain"|"1"|
    |"Tree Hits"|"does not contain"|"1"|
    |"Vehicle Hits"|"does not contain"|"1"|
    |"Water Hits"|"does not contain"|"1"|

  Scenario Outline: LIKE Operator
    Given I have a fresh conditional search
    When I create a conditional with field <field>, operator <operator>, and value <value>
    Then if there is a result, its field <field> should match the pattern <value>
  Examples:
    |field|operator|value|
    |"ID"|"matches pattern"|"8*"|
    |"Location 1"|"matches pattern"|"SH*"|
    |"Location 2"|"matches pattern"|"SH*"|
    |"Severity"|"matches pattern"|"Mino*"|
    |"Year"|"matches pattern"|"202*"|
    |"Region"|"matches pattern"|"Auckla*"|
    |"Speed Limit"|"matches pattern"|"1"|
    |"Precipitation"|"matches pattern"|"Fi*"|
    |"Frost or Wind"|"matches pattern"|"*nd"|
    |"Advised Speed"|"matches pattern"|"8*"|
    |"Holiday"|"matches pattern"|"Lab*"|
    |"Light"|"matches pattern"|"Twi*"|
    |"Street Lighting"|"matches pattern"|"*f"|
    |"Temporary Speed Limit"|"matches pattern"|"8*"|
    |"Territorial Local Authority"|"matches pattern"|"Wairo*"|
    |"Traffic Control"|"matches pattern"|"Sto*"|
    |"Crash Direction"|"matches pattern"|"Nor*"|
    |"Financial Year"|"matches pattern"|"*001/*"|
    |"State Highway"|"matches pattern"|"Y*"|
    |"Flat/Hill"|"matches pattern"|"*at"|
    |"Lanes"|"matches pattern"|"*"|
    |"Road Character"|"matches pattern"|"Br*"|
    |"Road Lanes"|"matches pattern"|"*"|
    |"Road Surface"|"matches pattern"|"Seal*"|
    |"Urban"|"matches pattern"|"Urb*"|
    |"Deaths"|"matches pattern"|"*"|
    |"Minor Injuries"|"matches pattern"|"*"|
    |"Serious Injuries"|"matches pattern"|"*"|
    |"Bicycles"|"matches pattern"|"*"|
    |"Buses"|"matches pattern"|"*"|
    |"Cars/Station Wagons"|"matches pattern"|"*"|
    |"Mopeds"|"matches pattern"|"*"|
    |"Motorcycles"|"matches pattern"|"*"|
    |"Other Vehicles"|"matches pattern"|"*"|
    |"Pedestrians"|"matches pattern"|"*"|
    |"School Buses"|"matches pattern"|"*"|
    |"SUVs"|"matches pattern"|"*"|
    |"Taxis"|"matches pattern"|"*"|
    |"Trucks"|"matches pattern"|"*"|
    |"Unknown Vehicles"|"matches pattern"|"*"|
    |"Bridge Hits"|"matches pattern"|"*"|
    |"Cliff/Bank Hits"|"matches pattern"|"*"|
    |"Debris Hits"|"matches pattern"|"*"|
    |"Ditch Hits"|"matches pattern"|"*"|
    |"Fence Hits"|"matches pattern"|"*"|
    |"Guard Rail Hits"|"matches pattern"|"*"|
    |"Building Hits"|"matches pattern"|"*"|
    |"Kerb Hits"|"matches pattern"|"*"|
    |"Objects Thrown/Dropped"|"matches pattern"|"*"|
    |"Other Objects Struck"|"matches pattern"|"*"|
    |"Embankment Hits"|"matches pattern"|"*"|
    |"Parked Vehicle Hits"|"matches pattern"|"*"|
    |"Phone Box, etc. Hits"|"matches pattern"|"*"|
    |"Post/Pole Hits"|"matches pattern"|"*"|
    |"Road Works Hits"|"matches pattern"|"*"|
    |"Slip/Flood Hits"|"matches pattern"|"*"|
    |"Stray Animal Hits"|"matches pattern"|"*"|
    |"Traffic Island Hits"|"matches pattern"|"*"|
    |"Traffic Sign Hits"|"matches pattern"|"*"|
    |"Train Hits"|"matches pattern"|"*"|
    |"Tree Hits"|"matches pattern"|"*"|
    |"Vehicle Hits"|"matches pattern"|"*"|
    |"Water Hits"|"matches pattern"|"*"|