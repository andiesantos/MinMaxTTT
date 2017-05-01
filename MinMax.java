/*
    John Edward Pascual
    Andrea Marie Santos
    T-4L
    May 1, 2017
        Update: Added documentation and cleaned up code
	
	This class will be used to check the best movement of the AI upon its turn.
*/
import java.util.ArrayList;

public class MinMax {
	private State currentState, next;
	private ArrayList<State> nextStates;
	private int moveCount, nextMove;
	private int[] toPut;
	private String name;

	public MinMax(State s, Boolean mm) {
		nextStates = new ArrayList<State>();
		name = setState(s, mm);
		value(s);
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
	public int max_value(State s) {
		int v = 0;
		Integer m = (Integer.MAX_VALUE) * -1;
		State next;
		if (s.nextStates.size() != 0) {
			for (int i=0; i<s.nextStates.size(); i++) {
				next = s.nextStates.get(i);
				v = value(next);
				if (v > m) {
					m = v;
					s.nextMove = i;
				}
			}
			next = s.nextStates.get(s.nextMove);
		}
		return m;
	}
	
	/* Get the min value of each successor (state) */
	public int min_value(State s) {
		int v = 0;
		Integer m = Integer.MAX_VALUE;
		State next;
		if (s.nextStates.size() != 0) {
			for (int i=0; i<s.nextStates.size(); i++) {
				next = s.nextStates.get(i);
				v = value(next);
				if (v < m) {
					m = v;
					s.nextMove = i;
				}
			}
			next = s.nextStates.get(s.nextMove);

		}
		return m;
	}

	/* Gets the value of X from the utility */
	public int value(State s) {
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
				ret = max_value(s);
				return ret;
			} else { // min node
				// s.m = Integer.MAX_VALUE;
				s.checkSuccessors();
				ret = min_value(s);
				return ret;
			}
		}
	}

}