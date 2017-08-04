game engine for the tic tac toe type game
-----------------------------------------

Game engine provides API for the main operations, such as:

   

 - create new game
 - register new player
 - make a move
 - get current game status

No persistance is used in this version.

GameRepository and GameBuilderRepository are in-memory hash-maps.

Back-end is implemented using Spring Boot. It provides simple REST controller and exposes game API through html REST requests.
