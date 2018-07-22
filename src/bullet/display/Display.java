package bullet.display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

// game actually uses gop.jar's display
// this is literally a copy of gop display class though

public class Display {

	private JFrame frame;
	private Canvas c;
	
	private String title;
	private int width, height;	
	
	public Display(String t, int w, int h){
		title = t;
		width = w;
		height = h;
		
		createDisplay();
	}
	
	private void createDisplay(){
		frame = new JFrame(title);
		frame.setSize(width, height);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setResizable(false);	
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);
		
		Dimension d = new Dimension(width, height);
		c = new Canvas();
		c.setPreferredSize(d);
		c.setMaximumSize(d);
		c.setMinimumSize(d);
		c.setFocusable(false);
		
		frame.add(c);
		frame.pack();
	}
	
	public Canvas getCanvas(){
		return c;
	}
	
	public JFrame getJFrame(){
		return frame;
	}
}
