Feature: Create new Game

In order to create a new game
As a Game Initiator
I want to provide a GameBlueprint for a new game and create it; and get Game id
and Player id (my id as a Player) 

Scenario: All players data is available and the game can be created
Given  The number of players in the GameBlueprint equals the actual size of the list of registered players
And The number of HumanPlayers equals 1 
When I provide the GameBlueprint
Then A new Game is created 
And I get a NewGameData with the Game id and Player id
