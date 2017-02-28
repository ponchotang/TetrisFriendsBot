#TetrisFriendsBot

This is a Bot developed in Java that plays either the 2P or 6P game mode on TetrisFriends at www.tetrisfriends.com

A demonstration of this bot can be found here: https://www.youtube.com/watch?v=Gh3iFrAFOwU

Note: at 1:05, the bot makes a bad move due to one of the actions not registering. This is an issue that occurs occasionally and I believe it is due to the delay for keypresses not being sufficient.

However, this blunder also shows the bots ability to recover.

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

One improvement is to improve the speed of the bot. The main issue with the bot is that inputting actions takes the bulk of the time. Unfortunately, this is a limitation with the Robot class as reducing the delay between actions causes it to not register the action. It may also be due to the game itself preventing quick succession of inputs


