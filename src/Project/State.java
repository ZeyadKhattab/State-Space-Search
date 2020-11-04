package Project;

public class State {
	int[][] grid;
	int gridH, gridW, numberOfMembers, totalSaved;
	Character ethan;
	IMF[] members;
	public State(int gridH, int gridW, int numberOfMembers, Character ethan, IMF[] members) {
		// TODO Auto-generated constructor stub
		grid = new int[gridH][gridW];
		this.numberOfMembers = numberOfMembers;
		ethan = this.ethan;
		this.members = members;
	}
}
