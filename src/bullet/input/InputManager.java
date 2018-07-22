package bullet.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import bullet.Handler;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener{

	private boolean[] keys = new boolean[256];
	public boolean w, a, s, d, shift, x, ctrl, esc, space, spaceReleased, p, e;
	public boolean leftPressed, mouseMove, doubleLeftClick;
	
	private float mouseX, mouseY;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
		 leftPressed = true;	
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		
		if(e.getClickCount() == 2){
			doubleLeftClick = true;
		}else{
			doubleLeftClick = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();		
	}

	@Override
	public void keyPressed(KeyEvent e) {		
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;		
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			spaceReleased = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void tick(double delta){
		w = keys[KeyEvent.VK_W];
		s = keys[KeyEvent.VK_S];
		a = keys[KeyEvent.VK_A];
		d = keys[KeyEvent.VK_D];
		shift = keys[KeyEvent.VK_SHIFT];
		x = keys[KeyEvent.VK_X];
		ctrl = keys[KeyEvent.VK_CONTROL];
		esc = keys[KeyEvent.VK_ESCAPE];	
		space = keys[KeyEvent.VK_SPACE];
		p = keys[KeyEvent.VK_P];
		e = keys[KeyEvent.VK_E];
		
		if(p){
			Handler.getGame().getGameState().setMapToBoss();
		}
		
		spaceReleased = false;
		
	}
	
	public Point2D.Double getMousePos(){
		return new Point2D.Double(mouseX, mouseY);
	}
	
	public Point2D.Double getMouseWorldPos(){
		return new Point2D.Double(mouseX + Handler.getGameCamera().getXOffset(), mouseY + Handler.getGameCamera().getYOffset());
	}
}
