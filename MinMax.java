import java.util.LinkedList;

public class MinMax {
	public int[][] board;
	public int max, xWin, oWin;
	public String winner;
	public boolean utility;
	public LinkedList<State> states;

	public MinMax() {
		this.winner = "";
		this.xWin = 0;
		this.oWin = 0;
		this.states = new LinkedList<State>();
	}

	public void printBoard() {
		System.out.println("-----");
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				System.out.print(this.board[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public int getMax() {
		return this.max = 99999;
		// do max(m,v) magic here
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public int[][] value() {
		if (this.utility == true) { // Utility state
			return this.board;
		}
		return this.board;
	}

	public void setWinner(String a) {
		this.winner = a;
		winBoard(a);
	}

	public boolean winBoard(String a) { // Utility state
		if (a.equals("X")) {
			this.xWin = 1;
			this.oWin = -1;
			this.utility = true;
		} else if (a.equals("O")) {
			this.xWin = -1;
			this.oWin = 1;
			this.utility = true;
		} else if (a.equals("DRAW")) {
			this.xWin = 0;
			this.oWin = 0;
			this.utility = true;
		} else {
			this.xWin = 0;
			this.oWin = 0;
			this.utility = false;
		}

		return this.utility;
	}

}