package bullet.items.ability;

import bullet.Handler;
import bullet.gfx.Assets;
import bullet.mob.Mob;
import bullet.mob.ShieldMob;

public class Shield extends SummonAbility {

	public boolean created = false;
	private ShieldMob shield;

	public Shield() {
		super(Assets.shield);
		
		defense = 5;
		descriptionStats = descriptionStats();
	}

	@Override
	public void tick(double delta) {

	}

	@Override
	public void use(double damageMult, double angle) {
		if((shield == null || !shield.isAlive()) && Handler.getPlayer().getMana() > 0){
			shield = new ShieldMob((int)Handler.getPlayer().getShiftedMidPos().x, (int)Handler.getPlayer().getShiftedMidPos().y,
					Mob.DEFAULT_MOB_SIZE, Mob.DEFAULT_MOB_SIZE);	
			Handler.getGame().getGameState().getMap().getEntityManager().addEntity(shield);
		}
	}
	
	@Override
	public void cleanup(){
		if(shield != null)
			shield.setAlive(false);
	}

	@Override
	public String description() {
		return "Shield\n"
				+ "Protects stuff\n" +
				descriptionStats + "\n"
				+ "Shield takes mana damage";
	}



}
