package code.mission;

public class Character {
	int posX, posY;

	public Character() {
		posX = 0;
		posY = 0;
	}

	public Character(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public Character clone() {
		return new Character(posX, posY);
	}
}