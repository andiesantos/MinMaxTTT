public class State {
	public int[][] board;
	public boolean maxNode;
	public boolean minNode;

	public State(int[][] board) {
		this.board = board;
		this.setMaxNode();
		this.setMinNode();
	}

	public void setMaxNode() {
		this.maxNode = true;
	}

	public void setMinNode() {
		this.minNode = true;
	}

}