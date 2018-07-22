package bullet.items;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import bullet.Handler;

//item tooltips

public class ToolTip {

	private static final int MARGIN = 12;
	private static final Font FONT = new Font("DialogInput", Font.BOLD, 17), SUBFONT = new Font("DialogInput", Font.PLAIN, 16);
	private Item i;
	
	public ToolTip(Item i){
		this.i = i;
	}

	public void render(Graphics2D g){
		
		int maxWidth = 0;
		int lines = 0;
		
		g.setColor(Color.darkGray);
		g.setFont(FONT);
		
		FontMetrics fm = g.getFontMetrics(FONT);
		for (String line : i.description().split("\n")){
	        if(fm.stringWidth(line) > maxWidth)
	        	maxWidth = fm.stringWidth(line);
	        
	        lines++;
		}
		
		RoundRectangle2D.Double r = new RoundRectangle2D.Double((int)Handler.getInput().getMousePos().x,(int) Handler.getInput().getMousePos().y, maxWidth + (2 * MARGIN), (fm.getHeight() * lines) + (2 * MARGIN),
				20, 20);
		g.fill(r);
	//	g.fillRect((int)Handler.getInput().getMousePos().x,(int) Handler.getInput().getMousePos().y, maxWidth + (2 * MARGIN), (fm.getHeight() * lines) + (2 * MARGIN));
		g.setColor(Color.CYAN);
		
		int y = (int) Handler.getInput().getMousePos().y;
		
		for (String line : i.description().split("\n")){
	        g.drawString(line, (int)Handler.getInput().getMousePos().x + MARGIN, y + fm.getHeight());
	        y += fm.getHeight();
	        g.setColor(Color.WHITE);
	        g.setFont(SUBFONT);
		}
		
		//g.drawString(i.description(), (int)Handler.getInput().getMousePos().x + 10,(int) Handler.getInput().getMousePos().y + 20);
	}
}
