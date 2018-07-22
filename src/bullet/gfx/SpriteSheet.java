package bullet.gfx;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class SpriteSheet extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;	
	}
		
	public BufferedImage crop(int x, int y, int w, int h){
		return sheet.getSubimage(x, y, w, h);
	}
	
}
