package bullet.items;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.ListIterator;

public class BagManager {
	public LinkedList<Bag> bags = new LinkedList<>();
	
	public void tick(double delta){
		
		ListIterator<Bag> iter = bags.listIterator();
		
		while(iter.hasNext()) {
			Bag current = iter.next();
			
			current.tick(delta);
			
			if(!current.isAlive())
				iter.remove();
		}
	}
	
	public void render(Graphics2D g){
		for(Bag b : bags){
			b.render(g);
		}
	}
	
	public void addBag(Bag b){
		bags.add(b);
	}
	
	public void removeBag(Bag b){
		bags.remove(b);
	}
}
