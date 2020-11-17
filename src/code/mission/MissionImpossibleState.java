package code.mission;


import code.generic.State;

public class MissionImpossibleState extends State {
	static int gridH, gridW, numberOfMembers, maxSaves;
	Character ethan;
	static Character submarine;
	IMF[] members;
	int currentCarry, totalSaved, totalDamage;

	// 5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3
	public MissionImpossibleState(String state) {
		String[] split = state.split(";");
		// Grid Dimensions
		String[] gridD = split[0].split(",");
		gridW = Integer.parseInt(gridD[0]);
		gridH = Integer.parseInt(gridD[1]);
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
		}
		// Max Saves Per Once
		String[] c = split[5].split(",");
		maxSaves = Integer.parseInt(c[0]);
		currentCarry = 0;
	}

	public MissionImpossibleState(int numberOfMembers, IMF[] members, int totalSaved,
			int currentCarry, int totalDamage, Character ethan){
		this.members = new IMF[numberOfMembers];

		this.totalSaved = totalSaved;

		this.currentCarry = currentCarry;
		this.totalDamage = totalDamage;
		this.ethan = ethan.clone();

		for (int i = 0; i < numberOfMembers; i++) {
			this.members[i] = members[i].clone();

		}
	}
	public MissionImpossibleState(){
		
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

	boolean pickUp(int memberIdx) {
		if (currentCarry == maxSaves)
			return false;
		IMF member = members[memberIdx];
		if (member.saved)
			return false;
		decreaseHealth(MissionImpossible.distance(ethan, member));
		currentCarry++;
		member.saved = true;
		ethan.posX = member.posX;
		ethan.posY = member.posY;
		decreaseHealth(1);
		return true;
	}

	boolean leave() {
		if (currentCarry > 0) {
			totalSaved += currentCarry;
			currentCarry = 0;
			decreaseHealth(MissionImpossible.distance(ethan, submarine)+1);
			ethan.posX = submarine.posX;
			ethan.posY = submarine.posY;
			
			return true;
		}
		return false;
	}

	int getRemainingMembers() {
		return numberOfMembers - totalSaved;
	}

	void decreaseHealth(int time) {

		for (IMF imf : members) {
			if (imf.saved)
				continue;
			int decrease = Math.min(2 * time, 100 - imf.health);
			imf.health += decrease;
			totalDamage += decrease;
		}
	}


	IMF[] getMembers() {
		return members;
	}

	IMF getMember(int index) {
		return members[index];
	}

	@Override
	public boolean equals(State other) {
		return this.compareTo(other) == 0;

	}

	public int getNumberOfDeaths() {
		int ans = 0;
		for (IMF member : members)
			if (member.health == 100)
				ans++;
		return ans;
	}

	@Override
	public MissionImpossibleState clone() {
		return new MissionImpossibleState(numberOfMembers, members, totalSaved, currentCarry, totalDamage, ethan);

	}

	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < gridH; i++) {
			for (int j = 0; j < gridW; j++) {
				String cell = "";
				if (ethan.posX == i && ethan.posY == j)
					cell += "ETHAN ";
				if (submarine.posX == i && submarine.posY == j)
					cell += "SUBM ";
				String mem = "NULL ";
				for (IMF m : members)
					if (m.posX == i && m.posY == j && !m.saved)
						mem = "(" + m.health + ")";
				cell += mem;
				while (cell.length() < 16)
					cell += " ";
				res += cell + "|";
			}
			res += "\n";
		}
		res += "Current Carry: " + currentCarry + ", Total Saved: " + totalSaved + ", Total Damage: " + totalDamage
				+ "\n";
		return res;
	}

	@Override
	public int compareTo(State other) {
		MissionImpossibleState s = (MissionImpossibleState) other;
		if (ethan.posX != s.ethan.posX)
			return Integer.compare(ethan.posX, s.ethan.posX);
		if (ethan.posY != s.ethan.posY)
			return Integer.compare(ethan.posY, s.ethan.posY);
		if (currentCarry != s.currentCarry)
			return Integer.compare(currentCarry, s.currentCarry);
		if (totalDamage != s.totalDamage)
			return Integer.compare(totalDamage, s.totalDamage);
		if (totalSaved != s.totalSaved)
			return Integer.compare(totalSaved, s.totalSaved);
		for (int i = 0; i < numberOfMembers; i++) {
			if (members[i].saved != s.members[i].saved)
				return members[i].saved ? 1 : -1;
			if (members[i].health != s.members[i].health)
				return Integer.compare(members[i].health, s.members[i].health);
		}
		return 0;
	}

}