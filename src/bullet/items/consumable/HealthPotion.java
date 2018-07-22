package bullet.items.consumable;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.items.Item;

public class HealthPotion extends Item implements Consumable {

	public HealthPotion() {
		super(Assets.healthPotion);
	}

	@Override
	public void use() {
		Handler.getPlayer().heal(10000);
	}

	@Override
	public String description() {
		return "Health Potion\nRestores 10000 HP";
	}

}
