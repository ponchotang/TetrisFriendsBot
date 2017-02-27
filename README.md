#TetrisFriendsBot

This is a Bot developed in Java that plays either the 2P or 6P game mode on TetrisFriends at www.tetrisfriends.com

#How it works

1. The bot detects the game through the BoardDetector class (using pixels from a screenshot)
2. The RealGame class uses BoardDetector in order to accurately represent the state of the TetrisFriends game. The state includes:
  * The current Tetrimino piece in play as well as the next two.
  * The current state of the board. (which tiles in the board are filled up etc..)
3. The bot then retrieves a list of all the possible moves for the three tetriminos using the MoveGenerator class.
4. The bot then loops through all three move lists in the form of a triple nested for loop
5. Within this loop, a SimulatedGame object is created, which replicates the state of the actual game.
6. It then simulates the current iteration of moves for each of the three tetriminos and calculates a score based on the resulting board state.
7. This repeats until all possible moves for the three tetriminos have been simulated.
8. The bot then takes the move of only the first tetrimino that results in the best score and executes it using the Inputer class.

NOTE: as of now, the bot is only considering two tetriminos in order to favour speed.

#Improvements

One possible improvement is to make it prioritise setting up a board state so that it can clear 3-4 lines at once.
This will require changes to the score calculation in order to do this.
For now, I have removed the height weight and introduced a sent lines factor as a temporary solution.
An issue with this solution is that since it is only considering two tetriminos, it only prioritises clearing multiple lines rather than setting up for a huge clear. This results in more doubles but barely any triples or tetrises as the bot does not read that far ahead.


