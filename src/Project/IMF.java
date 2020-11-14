package Project;

public class IMF extends Character {
	boolean saved;
	int health;

	public IMF(int posX, int posY, int health) {
		super(posX, posY);
		saved = false;
		this.health = health;
	}

	public IMF(int posX, int posY, int health, boolean saved) {
		this(posX, posY, health);
		this.saved = saved;
	}
}