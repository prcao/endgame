package bullet;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import bullet.display.Display;
import bullet.gfx.Assets;
import bullet.gfx.GameCamera;
import bullet.input.InputManager;
import bullet.mob.player.Player;
import bullet.state.GameState;

public class Game implements Runnable{

	private Display display;
	private int width, height;
	private String title;
	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics2D g;

	private GameState gameState;

	private InputManager input;

	private GameCamera gameCamera;

	//CONSTRUCTOR
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;

		Handler.setGame(this);
	}

	//STARTS THREAD
	public synchronized void start(){

		if(running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	//STOPS THREAD
	public synchronized void stop(){

		if(!running)
			return;

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		init();

		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 61;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   

		// keep looping round til the game ends
		while (running){

			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double)OPTIMAL_TIME);


			// update the game logic
			tick(delta);

			// draw everyting
			render();

			try{
				Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_TIME)/1000000 );
			}catch(Exception e){			};
		}

		

		//stops the thread
		stop();
		
		/*
		int fps = 60;
			double timePerTick = 1000000000 / fps;	//1 billion nanoseconds or 1000 milliseconds
			double delta = 0;
			long now;
			long lastTime = System.nanoTime();
			long timer = 0;
			int ticks = 0;

			//game loop
			while(running){
				now = System.nanoTime();
				delta += (now - lastTime)/timePerTick;

				timer += now - lastTime;

				lastTime = now;



		if(delta >= 1){
			tick();
			render();
			ticks++;
			delta--;
		}

		//fps counter
		if(timer>=1000000000){
			timer = 0;
			ticks = 0;
		}
	}*/
	}

	//UPDATES VARIABLES
	private void tick(double delta){
		gameState.tick(delta);
		input.tick(delta);
	}

	//RENDERS TO SCREEN
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(2);
			return;
		}

		//draws to screen
		g = (Graphics2D) bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);

		gameState.render(g);
		//ends the drawing
		bs.show();
		g.dispose();
	}



	private void init(){	
		
		
		input = new InputManager();

		display = new Display(title,width, height);
		display.getJFrame().addKeyListener(input);
		display.getCanvas().addKeyListener(input);
		display.getJFrame().addMouseListener(input);
		display.getCanvas().addMouseListener(input);
		display.getJFrame().addMouseMotionListener(input);
		display.getCanvas().addMouseMotionListener(input);

		Assets.init();		

		gameCamera = new GameCamera();	
		gameState = new GameState();
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}	

	public InputManager getInput(){
		return input;
	}

	public GameState getGameState(){
		return gameState;
	}

	public GameCamera getGameCamera(){
		return gameCamera;
	}
	
	public Player getPlayer(){
		return gameState.getPlayer();
	}
	
	public Graphics2D getGraphics(){
		return g;
	}
}
