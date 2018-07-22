package bullet.items.consumable;

import bullet.gfx.Assets;
import bullet.items.Item;

public class Instructions extends Item implements Consumable{

	public Instructions() {
		super(Assets.readMe);
	}

	@Override
	public void use() {
		
	}

	@Override
	public String description() {
		return "Controls:\n"
				+ "WASD move\n"
				+ "Shift + WASD to slow, shows hitbox\n"
				+ "Press/Hold space for ability\n"
				+ "Click to shoot\n"
				+ "double click consumables to consume\n"
				+ "Equipment slots are in this order: weapon/armor/ability/ring\n"
				+ "Strength effect doubles attack, haste doubles atk speed\n"
				+ "click and drag items to drop/rearrange\n"
				+ "Double click this manual to remove it";
	}

}
