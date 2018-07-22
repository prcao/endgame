package bullet.items.consumable;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.items.Item;
import bullet.world.lighting.Light;

public class Torch extends Item implements Consumable{

	public Torch() {
		super(Assets.torch);
	}

	@Override
	public void use() {
		
		Handler.getGame().getGameState().getMap().getEntityManager().addEntity(new Light(
				Handler.getGame().getGameState().getMap().getPlayer().getPos(),
				.5,
				Assets.torch
				));
		
	}
	
	@Override
	public String description(){
		return "Torch\nLights up the area when used";
	}

}
