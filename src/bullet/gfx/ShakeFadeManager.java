package bullet.gfx;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import bullet.Handler;
import bullet.mob.Mob;

public class ShakeFadeManager {
	
	private static Random r = new Random();
	private LinkedList<ShakeFade> shakeFades = new LinkedList<>();
	private Mob m;
	
	public ShakeFadeManager(Mob m){
		this.m = m;
	}
	
	public void tick(double delta){
		shakeFades.add(new ShakeFade(m.getCurrentFrame(), m));
		
		for(int i = 0; i < shakeFades.size(); i++){
			shakeFades.get(i).tick(delta);
			
			if(shakeFades.get(i).getOpacity() <= 0){
				shakeFades.remove(i);
			}
			
		}
	}
	
	public void render(Graphics2D g){
		for(ShakeFade s : shakeFades){
			s.render(g);
		}
	}
	
	class ShakeFade{
		
		private Point2D.Double pos;
		private BufferedImage image;
		private Mob m;
		private int velocityX = r.nextInt(10) - 5, velocityY = r.nextInt(10) - 5;
		private float opacity = .5f;
		
		public ShakeFade(BufferedImage image, Mob m){
			this.image = image;
			pos = m.getPos();
			this.m = m;
		}
		
		public void tick(double delta){
			opacity -= .05 * delta;
			
			pos.x += velocityX * delta;
			pos.y += velocityY * delta;		
			
		}
		
		public void render(Graphics2D g){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			
			g.drawImage(image, (int)pos.x - Handler.getGameCamera().getXOffset(), (int)pos.y - Handler.getGameCamera().getYOffset(), m.getWidth(), m.getHeight(), null);	
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
		
		public float getOpacity(){
			return opacity;
		}
	}
}
