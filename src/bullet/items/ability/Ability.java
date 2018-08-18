package bullet.items.ability;

import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.items.Item;

public abstract class Ability extends Item {

	protected long lastTime = System.currentTimeMillis(), timer = 0;
	protected double cooldown = -1;
	protected int manaCost = -1;
	
	public Ability(BufferedImage itemTexture) {
		super(itemTexture);
	}
	
	@Override
	public String descriptionStats() {
		
		String str = super.descriptionStats();
		
		if(cooldown > 0)
			str += "Cooldown: " + cooldown / 1000  + " seconds\n";
		if(manaCost != -1)
			str += "Mana Cost: " + manaCost + " mana\n";
		
		return str;
	}
	
	public abstract void use(double damageMult, double angle);
	
	//defaults to nothing
	public void cleanup(){
		
	}
	
	public void tick(double delta){
		
	}
	
	public double getCooldown(){
		return cooldown;
	}
	
	public int getManaCost(){
		return manaCost;
	}
	
	
}
