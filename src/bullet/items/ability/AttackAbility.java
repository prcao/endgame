package bullet.items.ability;

import java.awt.image.BufferedImage;

public abstract class AttackAbility extends Ability{

	protected BufferedImage projTexture;
	protected int projSpeed, projLifetime;
	
	public AttackAbility(BufferedImage itemTexture, BufferedImage projTexture, int projSpeed, int projLifetime) {
		super(itemTexture);
		
		this.projLifetime = projLifetime;
		this.projSpeed = projSpeed;
		this.projTexture = projTexture;
	}
	
}
