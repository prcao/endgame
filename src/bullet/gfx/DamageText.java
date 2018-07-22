package bullet.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import bullet.Handler;
import bullet.mob.Mob;

public class DamageText {

	public static Font font = new Font("Calibri", Font.BOLD, 24);
	public static final double DAMAGE_TEXT_INITIAL_VELOCITY = 2, DAMAGE_TEXT_SLOWDOWN = .1;
	private Point2D.Double pos;
	private int amount;
	private double velocity;
	private boolean isActive = true;
	private Color c;

	public DamageText(Mob m, int amount, Color c){
		Graphics2D g = Handler.getGame().getGraphics();
		FontMetrics metrics = g.getFontMetrics(font);
		pos = m.getPos();
		pos.x += m.getWidth()/2 - metrics.stringWidth(Integer.toString(-amount))/2;

		this.amount = amount;
		this.c = c;
		velocity = DAMAGE_TEXT_INITIAL_VELOCITY;
	}

	public void tick(double delta){
		pos.y -= velocity * delta;
		velocity -= DAMAGE_TEXT_SLOWDOWN;

		if(velocity < 0)
			isActive = false;
	}

	public void render(Graphics2D g){
		if(amount != 0){

			g.setFont(font);

			//outline effect lol
			g.setColor(Color.black);
			g.drawString(Integer.toString(-amount), (int)pos.x - Handler.getGameCamera().getXOffset() + 1, (int)pos.y - Handler.getGameCamera().getYOffset() + 1);
			g.drawString(Integer.toString(-amount), (int)pos.x - Handler.getGameCamera().getXOffset() + 1, (int)pos.y - Handler.getGameCamera().getYOffset() - 1);
			g.drawString(Integer.toString(-amount), (int)pos.x - Handler.getGameCamera().getXOffset() - 1, (int)pos.y - Handler.getGameCamera().getYOffset() + 1);
			g.drawString(Integer.toString(-amount), (int)pos.x - Handler.getGameCamera().getXOffset() - 1, (int)pos.y - Handler.getGameCamera().getYOffset() - 1);

			g.setColor(c);
			g.drawString(Integer.toString(-amount), (int)pos.x - Handler.getGameCamera().getXOffset(), (int)pos.y - Handler.getGameCamera().getYOffset());
		}
	}

	public boolean isActive(){
		return isActive;
	}
}

