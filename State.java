public class State {
	public int[][] board;
	public Boolean xWin, oWin, draw, maxNode, minNode;
	public int x, o;

	public State(int[][] board) {
		setBoard(board);
		xWin = false;
		oWin = false;
		draw = false;
		x = 0;
		o = 0;
	}

	public void setHardcode() { // For testing purposes only
		/*
		1 0 1
		1 2 0
		2 2 0
		*/
		this.board[0][0] = 1;
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
			x = 1;
			o = -1;
			return true;
		} else if (s.equals("O")) {
			oWin = true;
			x = -1;
			o = 1;
			return true;
		} else if (s.equals("DRAW")) {
			draw = true;
			x = 0;
			o = 0;
			return true;
		}
		return false;
	}

	public Boolean checkIfUtility() {
		if (setFinishedState("X") == true
			|| setFinishedState("O") == true
			|| setFinishedState("DRAW") == true) {
			System.out.println("DONE!");
			printState();
			return true;
		}
		return false;
	}

}