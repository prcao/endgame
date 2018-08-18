package bullet.mob.player;

import java.awt.Color;
import java.awt.Graphics2D;

import bullet.mob.Stats;

public class PlayerStats extends Stats {

	private Color lightBlue = new Color(0, 148, 255);
	
	private int mana, maxMana;
	private float manaScale, blueScale, manaRedScale;
	private Player player;
	
	public PlayerStats(Player p, int size) {
		super(p, size);
		
		player = p;
		
		maxMana = ((Player)m).getMana();
		mana = maxMana;

		manaScale = mana/maxMana;
		redScale = manaScale;
		greenScale = manaScale;
	}
	
	@Override
	public void tick(double delta){
		super.tick(delta);
		manaScale = ((float) ((Player)m).getMana()/maxMana);

		blueScale = manaScale;		

		if(manaScale < manaRedScale)
			manaRedScale -= .005;
		else
			manaRedScale = manaScale;
	}
	
	@Override
	public void render(Graphics2D g){
		g.setColor(Color.red);
		g.fillRect(0, (int) (Inventory.items[0].length * Inventory.size),
				(int) (Inventory.items.length * Inventory.size * redScale), 16);

		g.setColor(Color.GREEN);

		g.fillRect(0, (int) (Inventory.items[0].length * Inventory.size),
				(int) (Inventory.items.length * Inventory.size * greenScale), 16);
		
		g.setColor(Color.red);
		g.fillRect(0, (int) (Inventory.items[0].length * Inventory.size) + 16,
				(int) (Inventory.items.length * Inventory.size * manaRedScale), 16);

		g.setColor(lightBlue);
		g.fillRect(0, (int) (Inventory.items[0].length * Inventory.size) + 16,
				(int) (Inventory.items.length * Inventory.size * blueScale), 16);
		
//		g.fillRect(0, Inventory.items[0].length * Inventory.size, Inventory.items.length * Inventory.size, 48);
	}

}
