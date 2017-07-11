Feature: Register player for the game that is in the process of building


Scenario: register player for the game of 2 players HUMAN HUMAN
Given we have an id of the game
And the game for two players of types "HUMAN" and "HUMAN" is building
And the game status is "BUILDING"
When we register new player of type "HUMAN" for the game with the id 
Then the new player is registered and the player id is returned
And the new game status is "PLAYING"