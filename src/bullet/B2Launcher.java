package bullet;

// main method here, launches the game

public class B2Launcher {

	public static void main(String args[]){
		Game game = new Game("GAME", 1280, 720);
		
		game.start();
	}
}
