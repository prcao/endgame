package bullet.items.consumable;

import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.items.Item;
import bullet.mob.effect.StatusEffect;

public abstract class EffectPotion extends Item implements Consumable{

	protected StatusEffect effect;
	protected int duration;
	protected double potency;
	
	public EffectPotion(BufferedImage itemTexture, StatusEffect effect, int duration, double potency) {
		super(itemTexture);
		
		this.effect = effect;
		this.duration = duration;
		this.potency = potency;
		descriptionStats = descriptionStats();
	}
	
	@Override
	public String descriptionStats() {
		
		String str = "";
		
		str += "Duration: " + duration / 1000 + " seconds\n";
		str += "Potency: " + potency + "\n";
		
		str += super.descriptionStats();
		
		return str;
	}
	
	@Override
	public void use(){
		Handler.getPlayer().applyEffect(effect);
	}
	
	public int getDuration(){
		return duration;
	}
	
	public double getPotency(){
		return potency;
	}

}
