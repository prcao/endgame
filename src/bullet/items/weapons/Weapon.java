package bullet.items.weapons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import bullet.Handler;
import bullet.items.Item;
import bullet.mob.effect.StatusEffect;
import bullet.projectile.Projectile;
import bullet.world.Tile;

//stores info for a projectile so that when the player attacks, the weapon's projectile is created

public abstract class Weapon extends Item {

//	protected Projectile p;
	protected int baseDamage = 0, damageRange = 1, projSpeed = Projectile.DEFAULT_SPEED, projLifetime = 500, instability = 0;
	protected BufferedImage projTexture;
	protected StatusEffect effect = null;

	public Weapon(BufferedImage itemTexture, BufferedImage projTexture){
		super(itemTexture);

		this.projTexture = projTexture;

		type = ItemType.WEAPON;

	}
	
	@Override
	public String descriptionStats()  {
		
		String str = "";
		str += "Damage: " + baseDamage + "-" + (baseDamage + damageRange + "\n");
		str += "Range: " + Math.round(10*(projSpeed * ((projLifetime/1000.0)/(1.0/60))/Tile.TILE_SIZE))/10.0 + "\n";

		if(effect != null){
			str += "Effect: "
					+ effect.getClass().getSimpleName()
					+ " for " + effect.getDuration()/1000 + " seconds\n"
					+ "Potency: " + effect.getPotency() + "\n";
		}

		if(instability != 0){
			str += instability + " instability\n";
		}
		
		return str;
	}

	public int getBaseDamage() {
		return baseDamage;
	}

	public int getDamageRange() {
		return damageRange;
	}

	public BufferedImage getProjTexture(){
		return projTexture;
	}

	public int getProjLifetime(){
		return projLifetime;
	}

	public String toString(){
		return this.getClass().getSimpleName();
	}

	public int getProjSpeed() {
		return projSpeed;
	}

	//is overriden if there are multiple projectiles per shot
	public ArrayList<Projectile> getProjectile(double angle, double damageMult){
		Projectile p;
		
		if(instability != 0)
			p = new Projectile(projTexture, 48, 48, projLifetime, projSpeed, Handler.getPlayer(), angle + Math.toRadians(r.nextInt(instability) - instability/2), (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage)));
		else
			p = new Projectile(projTexture, 48, 48, projLifetime, projSpeed, Handler.getPlayer(), angle, (int)(damageMult * (new Random().nextInt(damageRange) + baseDamage)));
			
		p.setEffect(effect);
		
		ArrayList<Projectile> list = new ArrayList<Projectile>();
		list.add(p);

		return list;
	}
	
	public StatusEffect getEffect(){
		return effect;
	}

	public int getStability(){
		return instability;
	}
}

