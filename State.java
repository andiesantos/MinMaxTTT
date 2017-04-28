import java.util.ArrayList;

public class State {
	public int[][] board;
	public int[] utility, movement;
	public int x, o, m, v; // Refer to handout for use of the variables
	public Boolean xWin, oWin, draw, maxNode;
	public ArrayList<State> nextStates; // holder of result(s, a)

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
		// Get the 
		utility[1] = 0;
		movement = new int[3]; // index for next move + value to be put
		movement[0] = 0;
		movement[1] = 0;
		movement[2] = 0;
	}

	public void setHardcode() { // For testing purposes only
		
		// 1 0 1
		// 1 2 0
		// 2 2 0

		this.board[0][0] = 1;
		// this.board[0][1] = 1; // comment this out
		this.board[0][2] = 1;
		this.board[1][0] = 1;
		this.board[1][1] = 2;
		this.board[2][0] = 2;
		this.board[2][1] = 2;
	}

	public void setBoard(int[][] setboard) {
		this.board = setboard;
	}

	public void printState() {
		System.out.println("-----");
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
	}

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

	public String checker() {
        int countX = 0;
        int countO = 0;
        /*rows*/
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

        /*cols*/
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

        /*diagonal to the left*/
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

        /*diagonal to the right*/
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

        /*draw*/
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 2) {
                    countO++;
                } else if (board[i][j] == 1) {
                    countX++;
                }
            }

        }

        if (countX == 5) {
            countX = 0;
            countO = 0;
            setFinishedState("DRAW");
            return "DRAW";
        }

        return "";
    }

	public Boolean checkIfTerminal() {
		checker();
		if (xWin == true
			|| oWin == true
			|| draw == true) {
			System.out.println("DONE!");
			printState();
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

	public int[] getUtility() {
		return this.utility;
	}

	public void checkSuccessors() {
		/*
		Go through the grid
		Check for any 0 tiles
		Record the index of the next move
			make sure it depends on maxNode's value
			if maxNode == true then next symbol = 1
			else, symbol = 2
		Set it!!! to the next state
		then put that state into nextStates
		*/

		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				if (board[i][j] == 0) {
					if (maxNode == true) {	// MAXIMIZE X MOVEMENT
						movement[2] = 1;
					} else {
						movement[2] = 2;
					}
					movement[0] = i;
					movement[1] = j;
					setNextState(movement);
				}
			}
		}
	}

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
		printNextStates();
	}

	public void printNextStates() {
		System.out.println("CURRENT:");
		printState();
		for (int counter=0; counter<nextStates.size(); counter++) {
			System.out.println("=====");
			int[][] current = nextStates.get(counter).board;
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					System.out.print(current[i][j] + " ");
				}
				System.out.println("");
			}
		}
		System.out.println("\n\n");
	}
}