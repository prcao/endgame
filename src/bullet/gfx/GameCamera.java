package bullet.gfx;

import java.awt.geom.Point2D;

import bullet.Handler;
import bullet.mob.Mob;

public class GameCamera {

	private int xOffset, yOffset;
	private Point2D.Double mousePos;
	private double distanceX, distanceY;
	
	public GameCamera(){		
		mousePos = Handler.getInput().getMousePos();
	}
	
	public void center(Mob e){
		xOffset = (int) (e.getX() - Handler.getWidth()/2 + e.getWidth()/2);	//xy is in top left corner
		yOffset = (int) (e.getY() - Handler.getHeight()/2 + e.getHeight()/2); //-width/height /2 moves to middle, e.w/h centers the entity around screen
		
		checkBlank();
	}
	
	public void centerWithMouse(Mob e){
		//distance is the distance of mouse to center of screen
		mousePos = Handler.getInput().getMousePos();
		distanceX = (mousePos.x)- Handler.getWidth()/2;
		distanceY = (mousePos.y) - Handler.getHeight()/2;
		
		xOffset = (int) (e.getX() - Handler.getWidth()/2 + e.getWidth()/2 + distanceX/4);
		yOffset = (int) (e.getY() - Handler.getHeight()/2 + e.getHeight()/2 + distanceY/4);
		
		checkBlank();
	}
	
	//makes sure that the camera doesnt go offscreen
	public void checkBlank(){
		if(xOffset < 0)
			xOffset = 0;
		else if(xOffset > (Handler.getGame().getGameState().getMap().getWidth() - Handler.getWidth()))
			xOffset = Handler.getGame().getGameState().getMap().getWidth() - Handler.getWidth();
		
		if(yOffset < 0)
			yOffset = 0;
		else if(yOffset > (Handler.getGame().getGameState().getMap().getHeight() - Handler.getHeight()))
			yOffset = Handler.getGame().getGameState().getMap().getHeight() - Handler.getHeight();
		
	}
	
	public int getXOffset(){
		return xOffset;
	}
	
	public int getYOffset(){
		return yOffset;
	}
}
