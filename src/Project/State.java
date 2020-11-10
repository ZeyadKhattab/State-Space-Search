package Project;

public class State {
	IMF[][] grid;
	int gridH, gridW, numberOfMembers, totalSaved, maxSaves, curSaved;
	Character ethan, submarine;
	IMF[] members;
//	5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3
	public State(String state) {
		// TODO Auto-generated constructor stub
		String[] split = state.split(";");
		//Grid Dimensions
		String[] gridD = split[0].split(",");
		gridW = Integer.parseInt(gridD[0]);
		gridH = Integer.parseInt(gridD[1]);
		//Ethan
		String[] ethanP = split[1].split(",");
		ethan = new Character(Integer.parseInt(ethanP[0]), Integer.parseInt(ethanP[1]));
		//Submarine
		String[] subMarineP = split[2].split(",");
		submarine = new Character(Integer.parseInt(subMarineP[0]), Integer.parseInt(subMarineP[1]));
		//IMF Positions and Health
		String[] IMFP = split[3].split(",");
		String[] IMFH = split[4].split(",");
		numberOfMembers = IMFH.length;
		members = new IMF[numberOfMembers];
		for(int i = 0, j=0; i < IMFP.length;i+=2, j++)
		{
			int posX = Integer.parseInt(IMFP[i]);
			int posY = Integer.parseInt(IMFP[i+1]);
			int health = Integer.parseInt(IMFH[j]);
			members[j] = new IMF(posX, posY, health);
			grid[posX][posY] = members[j];
		}
		//Max Saves Per Once
		String[] c = split[5].split(",");
		maxSaves = Integer.parseInt(c[0]);
		curSaved = 0;
	}
	public State(int gridH, int gridW, int numberOfMembers, Character ethan, IMF[] members) {
		// TODO Auto-generated constructor stub
		grid = new IMF[gridH][gridW];
		this.numberOfMembers = numberOfMembers;
		this.ethan = ethan;
		this.members = members;
		totalSaved = 0;
	}
	
	Character getEthan() {
		return ethan;
	}
	
	boolean moveUp() {
		if(ethan.posX - 1 >= 0)
		{
			ethan.posX --;
			return true;
		}
		return false;
	}
	boolean moveDown() {
		if(ethan.posX + 1 < gridH)
		{
			ethan.posX ++;
			return true;
		}
		return false;
	}
	boolean moveLeft() {
		if(ethan.posY - 1 >= 0)
		{
			ethan.posY --;
			return true;
		}
		return false;
	}
	boolean moveRight() {
		if(ethan.posY + 1 < gridW)
		{
			ethan.posY ++;
			return true;
		}
		return false;
	}
	boolean pickUp() {
		if(grid[ethan.posX][ethan.posY] != null && curSaved != maxSaves) {
			grid[ethan.posX][ethan.posY].saved = true;
			grid[ethan.posX][ethan.posY] = null;
			curSaved++;
			return true;
		}
		return false;
	}
	boolean leave() {
		if(ethan.posX == submarine.posX && ethan.posY == submarine.posY)
		{
			totalSaved += curSaved;
			curSaved = 0;
			return true;
		}
		return false;
	}
	int getRemainingMembers(){
		return numberOfMembers - totalSaved;
	}
	
	IMF[] getMembers() { return members;}
	IMF getMember(int index) { return members[index];}
	
	IMF[][] getGrid(){
		return grid;
	}
}
