/*
	This class will be used to check the best movement of the AI upon its turn.
*/
import java.util.ArrayList;

public class MinMax {
	private State currentState;
	private ArrayList<State> states;

	public MinMax(State s) {
		states = new ArrayList<State>();
		setState(s);
		currentState.printState();
	}

	public void setState(State s) {
		this.currentState = s;
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
		// Return a utility state and store it in an ArrayList
		if (s.checkIfUtility() == true) {
			states.add(s);
		} else if ()
		return null;
	}

	public State bestMove() {
		/*
			Evaluates all the States and chooses the best one to maximize 
			chances of AI win
		*/
		return null;
	}
}