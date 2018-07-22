package bullet.state;

import java.awt.Graphics2D;

public abstract class State {
	
	public abstract void tick(double delta);
	public abstract void render(Graphics2D g);
}
