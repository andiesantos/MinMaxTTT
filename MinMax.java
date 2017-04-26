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
		// Get the max value of each successor (state)
		return 0;
	}
	
	public int min_value(State s) {
		// Get the min value of each successor (state)
		return 0;
	}

	public State value(State s) {
		System.out.println("Printing value...");
		if (s.checkIfTerminal() == true) {
			int[] util = s.getUtility();
			System.out.println(util[0] + " " + util[1]);
		} else {
			if (s.maxNode == true) { // max node
				s.m = (Integer.MAX_VALUE) * -1;
				// Check successors
			} else { // min node
				s.m = Integer.MAX_VALUE;
				// Check successors
			}
		}
		return null;
	}

}