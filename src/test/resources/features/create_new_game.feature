Feature: Create new Game

In order to create a new game
As a Game Initiator
I want to provide a GameBlueprint for a new game and create it; and get Game id
and Player id (my id as a Player)

In order to create a game a GameBuilder is created and the process of game building starts
The GameBuilder has its Id and returns it in response to create the new game
The GameBuilder can return GameData with info about the game that is being built 

In order to create a Game:
The number of players must be > 1
The PlayerType for all players should be specified
All players must be registered and get their Ids

Players of COMPUTER type are registered automatically and get their Ids
Players of Human type register themselves at any (non predictable) moment 

A Player sends a PlayerData and GameBuilder registers the Player (assigns him an Id) and returns his id
Having registered a player the GameBuilder checks whether all players are registered and if so creates a Game with an id that is the same as the id of GameBuilder. GameBuilder is deleted. Now Game answers the requests

If GameBuilder started building a Game, but the Game is not built yet all requests for Game status return GameBuildingResponse


Scenario: I create a game for two players (COMPUTER and HUMAN)
Given  The number of players in the GameBlueprint equals 2
And The PlayerType of the players is COMPUTER and HUMAN
And GameBluePrint contains PlayerData to register HUMAN Player 
When I provide the GameBlueprint
Then A new GameBuilder is created 
And Human Player is registered
And NewGameCreatedResponse is returned
