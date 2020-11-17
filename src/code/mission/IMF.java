package code.mission;
public class IMF extends Character {
	boolean picked;
	int health;

	public IMF(int posX, int posY, int health) {
		super(posX, posY);
		picked = false;
		this.health = health;
	}

	public IMF(int posX, int posY, int health, boolean saved) {
		this(posX, posY, health);
		this.picked = saved;
	}
	
	public IMF clone() {
		return new IMF(posX, posY, health, picked);
	}
}