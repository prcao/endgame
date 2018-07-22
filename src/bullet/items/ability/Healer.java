package bullet.items.ability;

import bullet.Handler;
import bullet.gfx.Assets;

public class Healer extends Ability {

	
	private boolean canUseAbility = true;

	public Healer() {
		super(Assets.healer);
		cooldown = 5000;
		manaCost = 75;
		
		descriptionStats = descriptionStats();
	}

	@Override
	public void use(double damageMult, double angle) {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer < cooldown)
			return;
		
		timer = 0;
		
		if(Handler.getPlayer().getMana() > manaCost){
			Handler.getPlayer().heal(50);

			Handler.getPlayer().setMana(Handler.getPlayer().getMana() - manaCost);

			canUseAbility = false;
		}
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Heal\n"
		+ "Heals 50 i think i dont remember sorry\n"
		+ descriptionStats;
	}

}
