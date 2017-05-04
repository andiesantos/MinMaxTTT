/*
    John Edward Pascual
    Andrea Marie Santos
    U-7L
    May 4, 2017
        Update: Tic tac toe implements "Alpha-beta pruning" algorithm
*/

import java.util.ArrayList;

public class State {
	public int[][] board;
	public int[] utility, movement;
	public int x, o, m, v, nextMove; // Refer to handout for variables' usage
	public Boolean xWin, oWin, draw, maxNode;
	public ArrayList<State> nextStates; // Holder of result(s, a)

	public State(int[][] board) {
		nextStates = new ArrayList<State>();
		setBoard(board);
		xWin = false;
		oWin = false;
		draw = false;
		x = 0;
		o = 0;
		utility = new int[2]; // X, O utility
		utility[0] = 0;
		utility[1] = 0;
		movement = new int[3]; // Index for next move + value to be put
		movement[0] = 0;
		movement[1] = 0;
		movement[2] = 0;
	}

	public void setBoard(int[][] setboard) {
		this.board = setboard;
	}

    /* Sets the Boolean values for this State */
    public Boolean setFinishedState(String s) {
        if (s.equals("X")) {
            xWin = true;
            oWin = false;
            draw = false;
            x = 1;
            o = -1;
            return true;
        } else if (s.equals("O")) {
            oWin = true;
            xWin = false;
            draw = false;
            x = -1;
            o = 1;
            return true;
        } else if (s.equals("DRAW")) {
            draw = true;
            xWin = false;
            oWin = false;
            x = 0;
            o = 0;
            return true;
        }
        return false;
    }

    /* Checks if there is a winner or a draw */
	public String checker() {
        int countX = 0;
        int countO = 0;
        /* Rows */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 2) {
                    countO++;
                } else if (board[i][j] == 1) {
                    countX++;
                }
            }

            if (countX == 3) {
            	setFinishedState("X");
                return "X";
            } else if (countO == 3) {
            	setFinishedState("O");
                return "O";
            }
            
            countX = 0;
            countO = 0;
        }

        /* Columns */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[j][i] == 2) {
                    countO++;
                } else if (board[j][i] == 1) {
                    countX++;
                }
            }
    
            if (countX == 3) {
            	setFinishedState("X");
                return "X";
            } else if (countO == 3) {
            	setFinishedState("O");
                return "O";
            }

            countX = 0;
            countO = 0;
        }

        /* Diagonal to the left */
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == 2) {
                countO++;
            } else if (board[i][i] == 1) {
                countX++;
            }
        }
        
        if (countX == 3) {
            setFinishedState("X");
            return "X";
        } else if (countO == 3) {
            setFinishedState("O");
            return "O";
        }

        countX = 0;
        countO = 0;

        /* Diagonal to the right */
        for (int i = 0; i < 3; i++) {
            if (board[i][2-i] == 2) {
                countO++;
            } else if (board[i][2-i] == 1) {
                countX++;
            }
        }
        
        if (countX == 3) {
            setFinishedState("X");
            return "X";
        } else if (countO == 3) {
            setFinishedState("O");
            return "O";
        }

        countX = 0;
        countO = 0;

        /* Draw */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 2) {
                    countO++;
                } else if (board[i][j] == 1) {
                    countX++;
                }
            }
        }

        if ((countX == 5) && (countO == 4)) {
            countX = 0;
            countO = 0;
            setFinishedState("DRAW");
            return "DRAW";
        }

        return "";
    }

    /* Checks if this State is a terminal State */
	public Boolean checkIfTerminal() {
		checker();
		if (xWin == true
			|| oWin == true
			|| draw == true) {
			//System.out.println("DONE!");
			if (xWin == true) { // X win(g)m
				this.utility[0] = 1;
				this.utility[1] = -1;
			} else if (oWin == true) { // O win
				this.utility[0] = -1;
				this.utility[1] = 1;
			} else if (draw == true) { // DRAW
				this.utility[1] = 0;
				this.utility[0] = 0;
			}
			return true;
		}
		return false;
	}

    /* Gets the succeeding States of the current State (per movement) */
	public void checkSuccessors() {
		/*
    		Go through the grid
    		Check for any 0 tiles
    		Record the index of the next move
    			make sure it depends on maxNode's value
    			if maxNode == true then next symbol = 1
    			else, symbol = 2
    		Set it to the next state
    		then put that State into nextStates
		*/

		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				if (board[i][j] == 0) {
					if (maxNode == true) {	// Maximize X Movement
						movement[2] = 1;
					} else { // Minimize X Movement
						movement[2] = 2;
					}
					movement[0] = i;
					movement[1] = j;
					setNextState(movement);
				}
			}
		}
	}

    /* Sets the next State according to the move in checkSuccessors() */
	public void setNextState(int[] movement) {
		int[][] newStateBoard = new int[3][3];
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				newStateBoard[i][j] = board[i][j];
			}
		}
		newStateBoard[movement[0]][movement[1]] = movement[2];
		State newState = new State(newStateBoard);
		if (this.maxNode == true) {
			newState.maxNode = false;
		} else {
			newState.maxNode = true;
		}
		nextStates.add(newState);
	}

	public void printNextStates() {
		for (int counter=0; counter<nextStates.size(); counter++) {
			int[][] current = nextStates.get(counter).board;
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					System.out.print(current[i][j] + " ");
				}
				System.out.println("");
			}
		}
	}

    public int[] getUtility() {
        return this.utility;
    }
}