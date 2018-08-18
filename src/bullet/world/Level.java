package bullet.world;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import bullet.entity.interactable.LevelStairs;
import bullet.gfx.Assets;

public class Level extends Map{

	
	
	public Level(BufferedImage map, Point stairPoint) {
		super(map);
		
		System.out.println(stairPoint);
		//stairs
		LevelStairs stairs = new LevelStairs(new Point2D.Double(stairPoint.getX(), stairPoint.getY()), Assets.stairs, this);
		entityManager.addEntity(stairs);
		interactableManager.interactables.add(stairs);
	}

	public Level(String path) {
		super(path);
		
	}

}
