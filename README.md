#TetrisFriendsBot

This is a Bot developed in Java that plays either the 2P or 6P game mode on TetrisFriends at www.tetrisfriends.com

#How it works

1. The bot detects the game through the BoardDetector class.
2. The RealGame class uses BoardDetector in order to accurately represent the state of the TetrisFriends game. The state includes:
  * The current Tetrimino piece in play as well as the next two.
  * The current state of the board. (which tiles in the board are filled up etc..)

