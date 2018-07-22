package bullet.mob;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import bullet.Handler;
import bullet.gfx.Assets;

// the mob that spawns when you use the shield ability lol

public class ShieldMob extends Mob{

	public ShieldMob(int x, int y, int width, int height) {
		super(x, y, Handler.getPlayer().getMana(), width, height, 0);
		
		type = Type.ALLY;
		
		def = 5;
		
		lightSource.setPotency(1);
	}
	
	public void tick(double delta){
		super.tick(delta);
		
		//bounds dont really matter for this mob
		//just used to create the hitbox
		bounds.setBounds((int)(pos.x  - Handler.getGameCamera().getXOffset()),
				(int) (pos.y - Handler.getGameCamera().getYOffset()), width/5, (int)(height * 1.3));

		pos = Handler.getPlayer().getShiftedMidPos();
		AffineTransform t = new AffineTransform();
		t.rotate(Math.toRadians(Handler.getPlayer().getAngle()),
				Handler.getPlayer().getShiftedMidPos().x - Handler.getGameCamera().getXOffset(),
				Handler.getPlayer().getShiftedMidPos().y - Handler.getGameCamera().getYOffset());
		t.translate(32, -height + 15);
		hitbox = t.createTransformedShape(bounds);
		
	}
	
	public void render(Graphics2D g){
		
		super.render(g);
		
		AffineTransform old = g.getTransform();
		AffineTransform t = new AffineTransform();
		t.rotate(Math.toRadians(Handler.getPlayer().getAngle() - 45),
				Handler.getPlayer().getShiftedMidPos().x - Handler.getGameCamera().getXOffset(),
				Handler.getPlayer().getShiftedMidPos().y - Handler.getGameCamera().getYOffset());
		
		g.setTransform(t);
		
		g.drawImage(getCurrentFrame(), (int)pos.x - Handler.getGameCamera().getXOffset(),
				(int)pos.y - Handler.getGameCamera().getYOffset(), width, height, null);
		
		g.setTransform(old);
		
		//	g.draw(hitbox);
	}
	
	//uses player's mana as health
	@Override
	public void hurt(int damage){
		
		def = Handler.getPlayer().getDef();
		
		damage = (int) Math.max(.15 * damage, damage - def);
		
		Handler.getPlayer().setMana(Handler.getPlayer().getMana() - damage);
		health -= damage;
		
		if(Handler.getPlayer().getMana() <= 0)
			die();
	}
	
	public BufferedImage getCurrentFrame(){
		return Assets.shieldProj;
	}
}
