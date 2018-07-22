package bullet.mob;

import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.gfx.Assets;

public class GoldShieldMob extends ShieldMob {

	public GoldShieldMob(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void hurt(int damage){
		def = Handler.getPlayer().getDef();
		
		damage = (int) Math.max(.15 * damage, damage - def);
		
		Handler.getPlayer().setMana(Handler.getPlayer().getMana() - damage);
		health -= damage;
		
		if(Handler.getPlayer().getMana() <= 0)
			die();
	}
	
	@Override
	public BufferedImage getCurrentFrame(){
		return Assets.goldShieldProj;
	}

}
