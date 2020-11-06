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
	
	Character getEthan() {
		return ethan;
	}
	
	boolean moveUp() {
		if(ethan.posY - 1 >= 0)
		{
			ethan.posY --;
			return true;
		}
		return false;
	}
	boolean moveDown() {
		if(ethan.posY + 1 <= gridH)
		{
			ethan.posY ++;
			return true;
		}
		return false;
	}
	boolean moveLeft() {
		if(ethan.posX - 1 >= 0)
		{
			ethan.posX --;
			return true;
		}
		return false;
	}
	boolean moveRight() {
		if(ethan.posX + 1 <= gridW)
		{
			ethan.posX ++;
			return true;
		}
		return false;
	}
	int getRemainingMembers(){
		return numberOfMembers - totalSaved;
	}
	
	IMF[] getMembers() { return members;}
	IMF getMember(int index) { return members[index];}
	
	int[][] getGrid(){
		return grid;
	}
}
