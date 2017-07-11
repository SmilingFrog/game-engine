Feature: get game status

Scenario: A player requests game status of the game that is in the process of building

Given a game for the players of type "HUMAN" and "HUMAN" is in the process of building
And the player has the game id
When the player requests game status and provides the game id 
Then the player gets game status result with the game status "BUILDING"

Scenario: A player requests game status of the game that is in the process of playing

Given a game for the players of type "HUMAN" and "HUMAN" is in the process of playing
And the player has the game id
When the player requests game status and provides the game id 
Then the player gets game status result with the game status "PLAYING"