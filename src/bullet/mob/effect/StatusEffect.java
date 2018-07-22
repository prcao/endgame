package bullet.mob.effect;

import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.mob.Mob;

public abstract class StatusEffect {

	public final static int DRAWSIZE = 16;

	protected BufferedImage icon;
	protected boolean isActive = true, hasBeenApplied = false;
	protected int duration = 0;
	protected double potency;

	protected long lastTime, timer, totalTimer, totalLastTime;

	public StatusEffect(BufferedImage icon, int duration, double potency){
		this.icon = icon;
		this.duration = duration;
		this.potency = potency;
	}

	public StatusEffect(StatusEffect e){
		this.icon = e.icon;
		this.duration = e.duration;
		this.potency = e.potency;
	}

	public abstract StatusEffect clone();

	public void effect(Mob m){
		if(!hasBeenApplied){
			timer = totalTimer = 0;
			lastTime = System.currentTimeMillis();
			hasBeenApplied = true;
		}else{
			if(isActive){
				totalTimer += System.currentTimeMillis() - lastTime;
				timer += System.currentTimeMillis() - lastTime;
				lastTime = System.currentTimeMillis();
			}
			isActive = totalTimer < duration + totalLastTime;
		}
	}

	public BufferedImage getIcon(){
		return icon;
	}

	public boolean isActive(){
		return isActive;
	}
	
	public void resetTimer(){
		totalTimer = 0;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public double getPotency(){
		return potency;
	}
}
