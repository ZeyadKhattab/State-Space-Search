package code.mission;


import code.generic.State;

public class MissionImpossibleState extends State {
	Character ethan;
	IMF[] members;
	int currentCarry, totalSaved, totalDamage;

	// 5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3
	public MissionImpossibleState(String state) {
		String[] split = state.split(";");
		// Grid Dimensions
		String[] gridD = split[0].split(",");
		MissionImpossible.gridW = Integer.parseInt(gridD[0]);
		MissionImpossible.gridH = Integer.parseInt(gridD[1]);
		// Ethan
		String[] ethanP = split[1].split(",");
		ethan = new Character(Integer.parseInt(ethanP[0]), Integer.parseInt(ethanP[1]));
		// Submarine
		String[] subMarineP = split[2].split(",");
		MissionImpossible.submarine = new Character(Integer.parseInt(subMarineP[0]), Integer.parseInt(subMarineP[1]));
		// IMF Positions and Health
		String[] IMFP = split[3].split(",");
		String[] IMFH = split[4].split(",");
		MissionImpossible.numberOfMembers = IMFH.length;
		members = new IMF[MissionImpossible.numberOfMembers];
		for (int i = 0, j = 0; i < IMFP.length; i += 2, j++) {
			int posX = Integer.parseInt(IMFP[i]);
			int posY = Integer.parseInt(IMFP[i + 1]);
			int health = Integer.parseInt(IMFH[j]);
			members[j] = new IMF(posX, posY, health);
		}
		// Max Saves Per Once
		String[] c = split[5].split(",");
		MissionImpossible.maxSaves = Integer.parseInt(c[0]);
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
		if (ethan.posX + 1 < MissionImpossible.gridH) {
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
		if (ethan.posY + 1 < MissionImpossible.gridW) {
			ethan.posY++;
			return true;
		}
		return false;
	}

	boolean pickUp(int memberIdx) {
		if (currentCarry == MissionImpossible.maxSaves)
			return false;
		IMF member = members[memberIdx];
		if (member.picked)
			return false;
		decreaseHealth(MissionImpossible.distance(ethan, member));
		currentCarry++;
		member.picked = true;
		ethan.posX = member.posX;
		ethan.posY = member.posY;
		decreaseHealth(1);
		return true;
	}

	boolean leave() {
		if (currentCarry > 0) {
			totalSaved += currentCarry;
			currentCarry = 0;
			decreaseHealth(MissionImpossible.distance(ethan, MissionImpossible.submarine)+1);
			ethan.posX = MissionImpossible.submarine.posX;
			ethan.posY = MissionImpossible.submarine.posY;
			
			return true;
		}
		return false;
	}

	int getRemainingMembers() {
		return MissionImpossible.numberOfMembers - totalSaved;
	}

	void decreaseHealth(int time) {

		for (IMF imf : members) {
			if (imf.picked)
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
		return new MissionImpossibleState(MissionImpossible.numberOfMembers, members, totalSaved, currentCarry, totalDamage, ethan);

	}

	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < MissionImpossible.gridH; i++) {
			for (int j = 0; j < MissionImpossible.gridW; j++) {
				String cell = "";
				if (ethan.posX == i && ethan.posY == j)
					cell += "ETHAN ";
				if (MissionImpossible.submarine.posX == i && MissionImpossible.submarine.posY == j)
					cell += "SUBM ";
				String mem = "NULL ";
				for (IMF m : members)
					if (m.posX == i && m.posY == j && !m.picked)
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
		for (int i = 0; i < MissionImpossible.numberOfMembers; i++) {
			if (members[i].picked != s.members[i].picked)
				return members[i].picked ? 1 : -1;
			if (members[i].health != s.members[i].health)
				return Integer.compare(members[i].health, s.members[i].health);
		}
		return 0;
	}

}
