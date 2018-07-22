package bullet;

import bullet.gfx.GameCamera;
import bullet.input.InputManager;
import bullet.mob.player.Player;

// class to get stuff

public class Handler {

	static Game game;
	
	public static void setGame(Game game) {
		Handler.game = game;
	}
	
	public static Game getGame(){
		return game;
	}
	
	public static InputManager getInput(){
		return game.getInput();
	}
	
	public static int getWidth(){
		return game.getWidth();
	}
	
	public static int getHeight(){
		return game.getHeight();
	}
	
	public static GameCamera getGameCamera(){
		return game.getGameCamera();
	}
	
	public static Player getPlayer(){
		return game.getPlayer();
	}
}
