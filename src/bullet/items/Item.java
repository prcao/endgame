package bullet.items;

import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Item {

	protected ItemType type;
	protected BufferedImage itemTexture;
	protected ToolTip ttip;
	protected int speed = 0, attack = 0, dex = 0, defense = 0, hp = 0;
	protected Random r = new Random();
	protected String description, descriptionStats;
	
	public Item(BufferedImage itemTexture){
		this.itemTexture = itemTexture;

		ttip = new ToolTip(this);
		
		
	}

	public BufferedImage getItemTexture(){
		return itemTexture;
	}

	public abstract String description();

	public ToolTip getToolTip(){
		return ttip;
	}

	public enum ItemType{
		WEAPON, ARMOR, CONSUMABLE
	}

	//tooltip description stuff
	
	// whats polymorphism?
	//dont roast me
	protected String descriptionStats(){
		String str = "";
		
		if(defense != 0){
			if(defense > 0)
				str += "+";

			str += defense + " defense\n";
		}
		
		if(speed != 0){
			if(speed > 0)
				str += "+";

			str += speed + " speed\n";
		}
		
		if(dex != 0){
			if(dex > 0)
				str += "+";

			str += dex + " dexterity\n";
		}
		
		if(attack != 0){
			if(attack > 0)
				str += "+";

			str += attack + " attack\n";
		}
		
		if(hp != 0){
			if(hp > 0)
				str += "+";

			str += hp + " HP\n";
		}

		return str;
	}

	public int getDefense(){
		return defense;
	}

	public int getSpeed(){
		return speed;
	}

	public int getAttack() {
		return attack;
	}

	public int getDex(){
		return dex;
	}

	public int getHP(){
		return hp;
	}
	
	public String getDescription(){
		return description;
		
	}
}
