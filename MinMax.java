/*
	This class will be used to check the best movement of the AI upon its turn.
*/
import java.util.ArrayList;

public class MinMax {
	private State currentState;
	private ArrayList<State> nextStates;
	private ArrayList<int[]> moves;
	private int moveCount, nextMove;

	public MinMax(State s) {
		nextStates = new ArrayList<State>();
		moves = new ArrayList<int[]>();
		setState(s);
		currentState.printState();
		value(s);
	}

	public void setState(State s) {
		this.currentState = s;
		s.maxNode = true; // starting state maximizes the chances of the AI win
	}

	public int max_value(State s) {
		int v = 0;
		// Get the max value of each successor (state)
		System.out.println(s.m);
		if (s.nextStates.size() != 0) {
			for (int i=0; i<s.nextStates.size(); i++) {
				State next = s.nextStates.get(i);
				next.checkSuccessors();
				if (next.nextStates.size() != 0) {
					v = value(next.nextStates.get(i));
					// max(m,v)
				} else {
					v = next.utility[0];
				}
				if (v > s.m) {
					s.m = v;
				}
			}
		}
		return s.m;
	}
	
	public int min_value(State s) {
		// Get the min value of each successor (state)
		return 0;
	}

	public int value(State s) {
		System.out.println("Printing value...");
		if (s.checkIfTerminal() == true) {
			int[] util = s.getUtility();
			System.out.println(util[0] + " " + util[1]);
			return util[0];
		} else {
			if (s.maxNode == true) { // max node
				s.m = (Integer.MAX_VALUE) * -1;
				s.checkSuccessors();
				return max_value(s);
			} else { // min node
				s.m = Integer.MAX_VALUE;
				s.checkSuccessors();
				return min_value(s);
			}
		}
	}

}