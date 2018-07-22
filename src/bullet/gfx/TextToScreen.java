package bullet.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import bullet.Handler;

public class TextToScreen {

	private static Font font = new Font("Courier New", Font.BOLD, 26);
	private int letters = 0;
	private long lastTime = System.currentTimeMillis(), timer = 0;
	private String string;

	private Point2D.Double posOffset = new Point2D.Double();

	public TextToScreen(String string){
		this.string = string;
	}

	public void tick(double delta){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if(timer < 40)
			return;

		timer = 0;

		if(letters < string.length())
			letters++;
	}

	public void render(Graphics2D g){
		printToScreen(g, string);
	}

	public void printToScreen(Graphics2D g, String message){
		
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		int width = fm.stringWidth(message.substring(0, letters));

		
		
		g.setColor(Color.black);
		g.drawString(message.substring(0, letters),(int) (Handler.getWidth()/2 - width/2 + posOffset.x + 2), (int) ((Handler.getHeight() * .90) + posOffset.y + 2));
		g.drawString(message.substring(0, letters),(int) (Handler.getWidth()/2 - width/2 + posOffset.x - 2), (int) ((Handler.getHeight() * .90) + posOffset.y - 2));
		g.drawString(message.substring(0, letters),(int) (Handler.getWidth()/2 - width/2 + posOffset.x - 2), (int) ((Handler.getHeight() * .90) + posOffset.y + 2));
		g.drawString(message.substring(0, letters),(int) (Handler.getWidth()/2 - width/2 + posOffset.x + 2), (int) ((Handler.getHeight() * .90) + posOffset.y - 2));
		
		g.setColor(Color.YELLOW);
		g.drawString(message.substring(0, letters),(int) (Handler.getWidth()/2 - width/2 + posOffset.x), (int) ((Handler.getHeight() * .90) + posOffset.y));
	}

	public void setString(String string){
		if(string != this.string){
			this.string = string;
			letters = 0;
		}
	}
}
