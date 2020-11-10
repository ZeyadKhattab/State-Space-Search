package Project;

public class IMF extends Character {
	boolean saved;
	int health;
	
	public IMF(int posX, int posY, int health) {
		super(posX, posY);
		saved = false;
		this.health = health;
	}
}