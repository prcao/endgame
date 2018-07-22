package bullet.mob;

import java.awt.Color;
import java.awt.Graphics2D;

import bullet.Handler;
import bullet.gfx.Assets;

public class Stats {

	protected float healthScale, redScale, greenScale;
	protected int maxHealth, health, size;
	protected Mob m;
	protected int comp;

	public Stats(Mob m, int size){
		this.m = m;
		this.size = size;

		maxHealth = m.getHealth();
		health = maxHealth;

		healthScale = health/maxHealth;
		redScale = healthScale;
		greenScale = healthScale;

		comp = m.getWidth()/2;
	}

	public void tick(double delta){
		healthScale = ((float) m.getHealth()/m.maxHealth());

		greenScale = healthScale;		

		if(healthScale < redScale)
			redScale -= .005;
		else
			redScale = healthScale;
	}

	public void render(Graphics2D g){

		g.setColor(Color.red);
		g.fillRect((int)(m.pos.x - Handler.getGameCamera().getXOffset() - comp + size/32), (int) (m.pos.y - Handler.getGameCamera().getYOffset() - 48),	//above head
				(int) (size * redScale - (Math.ceil(redScale * size/16))), 25);

		g.setColor(Color.GREEN);
		g.fillRect((int)(m.pos.x - Handler.getGameCamera().getXOffset() - comp + size/32), (int) (m.pos.y - Handler.getGameCamera().getYOffset() - 48),
				(int) (size * greenScale - (Math.ceil(greenScale * size/16))), 25);

		g.drawImage(Assets.healthBar,(int)(m.pos.x - Handler.getGameCamera().getXOffset() - comp), (int) (m.pos.y - Handler.getGameCamera().getYOffset() - 48), size, 25, null);

	}

	//renders health bar to constant place on the screen
	//(for bosses)
	public void renderAsHUD(Graphics2D g){

		g.setColor(Color.red);
		g.fillRect(0, (int) (Handler.getHeight() - 25),
				(int) (Handler.getWidth() * redScale), 25);

		g.setColor(Color.GREEN);
		g.fillRect(0, (int) (Handler.getHeight() - 25),
				(int) (Handler.getWidth() * greenScale), 25);

	}

	public void setSize(int size){
		this.size = size;
	}

	//obsolete code
//	public void damage(int amount){
//		if(health > 0){
//			health -= amount;
//		}else{
//			health = 0;
//		}
//	}

	public void setComp(int comp){
		this.comp = comp;
	}
}
