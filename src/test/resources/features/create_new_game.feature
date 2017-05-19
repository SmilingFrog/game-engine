Feature: Create new Game

In order to create a new game
As a Game Initiator
I want to provide a GameBlueprint for a new game and create it; and get Game id
and Player id (my id as a Player) 

Scenario: I provide a GameBlueprint for a new game and get id of GameBuilder
Given  The number of players in the GameBlueprint equals the actual size of the list of registered players 
When I provide the GameBlueprint
Then A new GameBuilder is created 
And I get a NewGameData with the Game id and GameData
