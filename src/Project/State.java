package Project;

public class State {
	IMF[][] grid;
	int gridH, gridW, numberOfMembers, totalSaved, maxSaves, currentCarry, totalDamage;
	Character ethan, submarine;
	IMF[] members;

	// 5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3
	public State(String state) {
		String[] split = state.split(";");
		// Grid Dimensions
		String[] gridD = split[0].split(",");
		gridW = Integer.parseInt(gridD[0]);
		gridH = Integer.parseInt(gridD[1]);
		grid = new IMF[gridH][gridW];
		// Ethan
		String[] ethanP = split[1].split(",");
		ethan = new Character(Integer.parseInt(ethanP[0]), Integer.parseInt(ethanP[1]));
		// Submarine
		String[] subMarineP = split[2].split(",");
		submarine = new Character(Integer.parseInt(subMarineP[0]), Integer.parseInt(subMarineP[1]));
		// IMF Positions and Health
		String[] IMFP = split[3].split(",");
		String[] IMFH = split[4].split(",");
		numberOfMembers = IMFH.length;
		members = new IMF[numberOfMembers];
		for (int i = 0, j = 0; i < IMFP.length; i += 2, j++) {
			int posX = Integer.parseInt(IMFP[i]);
			int posY = Integer.parseInt(IMFP[i + 1]);
			int health = Integer.parseInt(IMFH[j]);
			members[j] = new IMF(posX, posY, health);
			grid[posX][posY] = members[j];
		}
		// Max Saves Per Once
		String[] c = split[5].split(",");
		maxSaves = Integer.parseInt(c[0]);
		currentCarry = 0;
	}

	public State() {

	}

	Character getEthan() {
		return ethan;
	}

	boolean moveUp() {
		if (ethan.posX - 1 >= 0) {
			ethan.posX--;
			return true;
		}
		return false;
	}

	boolean moveDown() {
		if (ethan.posX + 1 < gridH) {
			ethan.posX++;
			return true;
		}
		return false;
	}

	boolean moveLeft() {
		if (ethan.posY - 1 >= 0) {
			ethan.posY--;
			return true;
		}
		return false;
	}

	boolean moveRight() {
		if (ethan.posY + 1 < gridW) {
			ethan.posY++;
			return true;
		}
		return false;
	}

	boolean pickUp() {
		if (grid[ethan.posX][ethan.posY] != null && currentCarry != maxSaves) {
			grid[ethan.posX][ethan.posY].saved = true;
			grid[ethan.posX][ethan.posY] = null;
			currentCarry++;
			return true;
		}
		return false;
	}

	boolean leave() {
		if (ethan.posX == submarine.posX && ethan.posY == submarine.posY && currentCarry > 0) {
			totalSaved += currentCarry;
			currentCarry = 0;
			return true;
		}
		return false;
	}

	int getRemainingMembers() {
		return numberOfMembers - totalSaved;
	}

	void decreaseHealth() {

		for (IMF imf : members) {
			if (imf.saved)
				continue;
			int decrease = Math.min(2, imf.health);
			imf.health -= decrease;
			totalDamage += decrease;
		}
	}

	IMF[] getMembers() {
		return members;
	}

	IMF getMember(int index) {
		return members[index];
	}

	IMF[][] getGrid() {
		return grid;
	}

	public boolean equals(State s) {
		boolean result = true;

		for (int i = 0; i < numberOfMembers; i++) {
			result = result && (s.members[i].saved == members[i].saved) && (s.members[i].posX == members[i].posX)
					&& (s.members[i].posY == members[i].posY && s.members[i].health == members[i].health);
		}
		result = result && (s.gridW == gridW) && (s.gridH == gridH) && (s.ethan.posX == ethan.posX)
				&& (s.ethan.posX == ethan.posX) && (s.ethan.posY == ethan.posY) && (s.submarine.posX == submarine.posX)
				&& (s.submarine.posY == submarine.posY);
		result = result && currentCarry == s.currentCarry && totalDamage == s.totalDamage && totalSaved == s.totalSaved;
		return result;

	}

	public int getNumberOfDeaths() {
		int ans = 0;
		for (IMF member : members)
			if (member.health == 0)
				ans++;
		return ans;
	}

	public State clone() {
		State newState = new State();
		newState.gridH = gridH;
		newState.gridW = gridW;
		newState.grid = new IMF[gridH][gridW];
		newState.members = new IMF[numberOfMembers];
		newState.numberOfMembers = numberOfMembers;
		newState.totalSaved = totalSaved;
		newState.maxSaves = maxSaves;
		newState.currentCarry = currentCarry;
		newState.totalDamage = totalDamage;
		newState.ethan = new Character(ethan.posX, ethan.posY);
		newState.submarine = new Character(submarine.posX, submarine.posY);

		for (int i = 0; i < numberOfMembers; i++) {
			newState.members[i] = new IMF(members[i].posX, members[i].posY, members[i].health, members[i].saved);
			if (!members[i].saved)
				newState.grid[members[i].posX][members[i].posY] = newState.members[i];
		}
		return newState;

	}

	public String toString() {
		String res = "";
		for (int i = 0; i < gridH; i++) {
			for (int j = 0; j < gridW; j++) {
				if (ethan.posX == i && ethan.posY == j)
					res += "ETHAN";
				if (submarine.posX == i && submarine.posY == j)
					res += "SUBM";
				if (grid[i][j] == null)
					res += ("NULL ");
				else
					res += ("(" + grid[i][j].health + ")");
				res += "|";
			}
			res += "\n";
		}
		res += "C and S: " + currentCarry + " " + totalSaved + "\n";
		return res;
	}
}
