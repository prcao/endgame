package bullet.gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	public static final int DEFAULT_ASPD = 300;

	private ArrayList<BufferedImage> frames;
	private volatile boolean running = false;
	public BufferedImage sprite;

	public long speed;
	private long lastTime, timer;
	private int frameAtPause, currentFrame;
	
	public Animation(ArrayList<BufferedImage> frames, long speed){
		this.frames = frames;
		this.speed = speed;
		
		sprite = frames.get(0);
		
		running = true;
		lastTime = System.currentTimeMillis();
	}

	public void setSpeed(long speed){
		this.speed = speed;
	}

	public void tick(){		
		if(running){
			sprite = frames.get(currentFrame);
			
			timer += System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			
			if(timer >= speed){
				currentFrame++;
				timer = 0;
				
				if(currentFrame >= frames.size())
					currentFrame = 0;
			}
		}
	}
	
	public BufferedImage getCurrentAnimationFrame(){
		return frames.get(currentFrame);
	}

	public void play(){
		running = true;
		lastTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}

	public void stop(){
		running = false;
		lastTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}

	public void pause(){
		frameAtPause = currentFrame;
		running = false;
	}

	public void resume(){
		currentFrame = frameAtPause;
		running = true;
	}

	public boolean isDoneAnimating(){
		if(currentFrame == frames.size())
			return false;
		return false;
	}

	public void reset(){
		currentFrame = 0;
	}
	
	public ArrayList<BufferedImage> getFrames(){
		return frames;
	}
}
