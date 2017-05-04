/*
    John Edward Pascual
    Andrea Marie Santos
    U-7L
    May 4, 2017
        Update: Tic tac toe implements "Alpha-beta pruning" algorithm
	
	This class will be used to check the best movement of the AI upon its turn.
*/
import java.util.ArrayList;

public class MinMax {
	private State currentState, next;
	private ArrayList<State> nextStates;
	private int moveCount, nextMove;
	private int[] toPut;
	private String name;
	private int alpha = -99;
	private int beta = 99;

	public MinMax(State s, Boolean mm) {
		nextStates = new ArrayList<State>();
		name = setState(s, mm);
		value(s, alpha, beta);
		next = s.nextStates.get(s.nextMove);
	}

	/* Get the next move of the AI as recommended by the MinMax algorithm */
	public int[] getNextMove() {
		// Compare new state with the current board
		int[] nextMove = new int[2];
		nextMove[0] = 10;
		nextMove[1] = 10;
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				if (currentState.board[i][j] != next.board[i][j]) {
					nextMove[0] = i;
					nextMove[1] = j;
					break;
				}
			}
		}
		return nextMove;
	}

	public String setState(State s, Boolean mm) {
		this.currentState = s;
		s.maxNode = mm; // starting state maximizes the chances of the AI win

		// Checks if the game is a draw
		int countO = 0;
		int countX = 0;
		// If true, max; else, min
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (s.board[i][j] == 2) {
                    countO++;
                } else if (s.board[i][j] == 1) {
                    countX++;
                }
            }

        }

        if ((countX == 5) && (countO == 4)) {
            countX = 0;

            countO = 0;
            return "draw";
        }
        return "";
	}

	/* Get the max value of each successor (state) */
	public int max_value(State s, int a, int b) {
		int v = (Integer.MAX_VALUE) * -1;
		// Integer alpha = (Integer.MAX_VALUE) * -1;
		State next;
		if (s.nextStates.size() != 0) {
			for (int i=0; i<s.nextStates.size(); i++) {
				next = s.nextStates.get(i);
				v = value(next, a, b);
				
				// checks if v has the maximum value compare to beta
				if (v >= b)
					return v;

				// gets the maximum value in either beta or v
				if (v > a) {
					a = v;
					s.nextMove = i;
				}
			}
			next = s.nextStates.get(s.nextMove);
		}
		return a;
	}
	
	/* Get the min value of each successor (state) */
	public int min_value(State s, int a, int b) {
		int v = Integer.MAX_VALUE;
		// Integer beta = Integer.MAX_VALUE;
		State next;
		if (s.nextStates.size() != 0) {
			for (int i=0; i<s.nextStates.size(); i++) {
				next = s.nextStates.get(i);
				v = value(next, a, b);
	
				// checks if v has the minimum value compare to alpha
				if (v <= a)
					return v;

				// gets the minimum value in either beta or v
				if (v < b) {
					b = v;
					s.nextMove = i;
				}
			}
			next = s.nextStates.get(s.nextMove);

		}
		return b;
	}

	/* Gets the value of X from the utility */
	public int value(State s, int a, int b) {
		int ret = 1000;
		if (s.checkIfTerminal() == true) {
			int[] util = s.getUtility();
			s.nextMove = 0;
			ret = util[0];
			return ret;
		} else {
			if (s.maxNode == true) { // max node
				// s.m = (Integer.MAX_VALUE) * -1;
				s.checkSuccessors();
				ret = max_value(s, a, b);
				return ret;
			} else { // min node
				// s.m = Integer.MAX_VALUE;
				s.checkSuccessors();
				ret = min_value(s, a, b);
				return ret;
			}
		}
	}

}