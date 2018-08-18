package bullet.mob.enemy;

import java.util.ArrayList;

import bullet.world.Tile;

public class AStar {

	private static final int D_MOVE = 14, H_V_MOVE = 10;
	private ArrayList<Tile> openList = new ArrayList<>();
	private ArrayList<Tile> closedList = new ArrayList<>();
	private Tile currentNode;
	
	public AStar(Tile startNode){
		openList.add(startNode);
		currentNode = startNode;
	}
	
	public int[] pathfind(Tile goal){
		
		while(!openList.isEmpty()){
			if(currentNode.equals(goal)){
				
			}
		}
		
		return new int[0];
	}
	
	public static int[] simplePathfind(Tile start, Tile goal){
		int gcost = Math.abs(goal.x - start.x) + Math.abs(goal.y - start.y);
		int fcost;
		int minfcost = Integer.MAX_VALUE;
		Tile minfcostTile = null;
		
		Tile[] neighbors = new Tile[9];
		int x = start.getTileX(), y = start.getTileY();
		
		for(int i = x - 1; i < x - 1 + 3; i++){
			for(int j = y - 1; j < y - 1 + 3; j++){
				if(i == 0 || j == 0)
					continue;
				
				fcost = gcost + ((i != x || j != y) ? D_MOVE : H_V_MOVE);
				
				if(fcost < minfcost){
					minfcost = fcost;
					minfcostTile = Tile.tileMatrix[i][j];
				}
			}
		}
		
		int[] toReturn = new int[2];
		toReturn[0] = x - minfcostTile.getTileX();
		toReturn[1] = y - minfcostTile.getTileY();
		
		return toReturn;
	}
}
