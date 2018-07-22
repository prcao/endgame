package bullet.entity;

import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.LinkedList;

public class EntityManager {

	public LinkedList<Entity> entities = new LinkedList<>();

	//sorts the render order so that entities that are at the bottom are rendered on top
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){

		@Override
		public int compare(Entity a, Entity b) {
			if(a.getPos().y + a.getHeight() < b.getPos().y + b.getHeight())
				return -1;
			else
				return 1;
		}

	};

	public void tick(double delta){
		
		for(int i = 0; i < entities.size(); i++) {
			Entity current = entities.get(i);
			
			current.tick(delta);
			
			if(!current.isAlive()) {
				entities.remove(current);
			}
		}
		
		//Unsure why this throws a concurrentModificationException
		//TODO: fix
//		ListIterator<Entity> iter = entities.listIterator();
//
//		while(iter.hasNext()) {
//			Entity current = iter.next();
//			current.tick(delta);
//
//			if(!current.isAlive()) 
//				iter.remove();
//		}
//
//		entities.sort(renderSorter);

	}

	public void render(Graphics2D g){
		for(Entity e : entities){
			e.render(g);
		}
	}

	public void addEntity(Entity e){
		entities.add(e);
	}

	public void removeEntity(Entity e){
		entities.remove(e);
	}
}
