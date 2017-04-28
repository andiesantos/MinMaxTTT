/*
	This class will be used to check the best movement of the AI upon its turn.
*/
import java.util.ArrayList;

public class MinMax {
	private State currentState;
	private ArrayList<State> nextStates;
	private int moveCount, nextMove;
	private int[] toPut;

	public MinMax(State s) {
		nextStates = new ArrayList<State>();
		moves = new ArrayList<int[]>();
		setState(s);
		currentState.printState();
		System.out.println("FINALLY " + value(s));
		System.out.println("====FINAL MOVE " + s.nextMove + "====");
		State next = s.nextStates.get(s.nextMove);
		s.printState();
		next.printState();
	}

	public void setState(State s) {
		this.currentState = s;
		s.maxNode = true; // starting state maximizes the chances of the AI win
	}

	public int max_value(State s) {
		int v = 0;
		Integer m = (Integer.MAX_VALUE) * -1;
		State next;
		// Get the max value of each successor (state)
		System.out.println(s.m);
		if (s.nextStates.size() != 0) {
			for (int i=0; i<s.nextStates.size(); i++) {
				next = s.nextStates.get(i);
				v = value(next);
				if (v > m) {
					m = v;
					s.nextMove = i;
					System.out.println("Set new nextMove");
				}
			}
			System.out.println("====NEXT MAX MOVE " + s.nextMove + "====");
			s.printState();
			next = s.nextStates.get(s.nextMove);
			next.printState();
		}
		System.out.println("\n\n\n");
		return m;
	}
	
	public int min_value(State s) {
		int v = 0;
		Integer m = Integer.MAX_VALUE;
		State next;
		// Get the min value of each successor (state)
		System.out.println(s.m);
		if (s.nextStates.size() != 0) {
			for (int i=0; i<s.nextStates.size(); i++) {
				next = s.nextStates.get(i);
				v = value(next);
				if (v < m) {
					m = v;
					s.nextMove = i;
					System.out.println("Set new nextMove");
				}
			}
			System.out.println("====NEXT MIN MOVE " + s.nextMove + "====");
			s.printState();
			next = s.nextStates.get(s.nextMove);
			next.printState();

		}
		System.out.println("\n\n\n");
		return m;
	}

	public int value(State s) {
		if (s.checkIfTerminal() == true) {
			int[] util = s.getUtility();
			System.out.println(util[0] + " " + util[1]);
			s.nextMove = 0;
			return util[0];
		} else {
			if (s.maxNode == true) { // max node
				// s.m = (Integer.MAX_VALUE) * -1;
				s.checkSuccessors();
				return max_value(s);
			} else { // min node
				// s.m = Integer.MAX_VALUE;
				s.checkSuccessors();
				return min_value(s);
			}
		}
	}

}