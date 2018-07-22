package bullet.world;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MapGenerator {

	public static final String STONE_TILE  = "#000000",
			STONE_WALL = "#404040",
			STAIRS = "#000001";
	private static Random r = new Random();
	private static int size = 1;

	public static MapAndStairPoint generateDungeon(int numRooms, int size, int maxRoomSize, Point playerSpawn){
		ArrayList<Room> rooms = new ArrayList<>(numRooms);

		//player spawn room
		Room playerStart = new Room((playerSpawn.x)/Tile.TILE_SIZE - 2, (playerSpawn.y)/Tile.TILE_SIZE - 2, 5, 5);
		rooms.add(playerStart);

		BufferedImage map = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

		//fill initial map with walls
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				map.setRGB(x, y, Color.decode(STONE_WALL).getRGB());
			}
		}

		//generate rooms
		for(int i = 0; i < numRooms; i++){
			int roomW = r.nextInt(maxRoomSize - 3) + 3, roomH = r.nextInt(maxRoomSize - 3) + 3;
			Room room = new Room(r.nextInt(size - roomW - 1) + 1, r.nextInt(size - roomH - 1) + 1, roomW, roomH);

			rooms.add(room);
		}

		//sets neighbors for each rooms, and draw rooms to map
		for(Room ro : rooms){
			ro.setNeighbors(rooms, size / 21);

			//draws to map
			for(int x = ro.x; x - ro.x < ro.width; x++){
				for(int y = ro.y; y - ro.y < ro.height; y++){

					if(map.getRGB(x, y) == Color.decode(STONE_WALL).getRGB())
						map.setRGB(x, y, Color.decode(STONE_TILE).getRGB());
				}
			}
		}
		
		RoomGraph graph = new RoomGraph(rooms);

		//draws corridors
		for(Room[] e : graph.edges){
			int[] currentPlace = { (int) e[0].getCenterX(), (int) e[0].getCenterY() };
			int[] destination = { (int) e[1].getCenterX(), (int) e[1].getCenterY() };
			while(!Arrays.equals(currentPlace, destination)){

				int rand = r.nextInt(2);

				if(currentPlace[rand] < destination[rand])
					currentPlace[rand]++;
				else if (currentPlace[rand] > destination[rand])
					currentPlace[rand]--;

				map.setRGB(currentPlace[0], currentPlace[1], Color.decode(STONE_TILE).getRGB());
			}
		}
		
		//stairs room
		Room endRoom = rooms.get(rooms.size() - 1);
		//map.setRGB(r.nextInt(endRoom.width + 1) + endRoom.x, r.nextInt(endRoom.height + 1) + endRoom.y, Color.decode(STAIRS).getRGB());
		Point point = new Point(Tile.TILE_SIZE * (endRoom.x + endRoom.width / 2), Tile.TILE_SIZE * (endRoom.y + endRoom.height / 2));//new Point(r.nextInt(Tile.TILE_SIZE * ((endRoom.width + 1) + endRoom.x)), Tile.TILE_SIZE * (r.nextInt(endRoom.height + 1) + endRoom.y));
		return new MapAndStairPoint(point, map);
	}

	static class Room extends Rectangle{

		ArrayList<Room> neighbors = new ArrayList<>();

		public Room(int x, int y, int width, int height){
			super(x, y, width, height);
		}

		public void setNeighbors(ArrayList<Room> allNodes, double threshold){

			for(int i = 0; i < allNodes.size(); i++){
				if(!allNodes.get(i).equals(this) && calculateDistance(this, allNodes.get(i)) <= threshold){
					neighbors.add(allNodes.get(i));
				}
			}

			//corridor to closest other node if no neighbors within threshold found
			if(neighbors.size() == 0){

				//finds min distance node
				Room minDistanceNode = null;
				double minDistance = Integer.MAX_VALUE;
				for(Room node : allNodes){
					if(!node.equals(this)){
						double calcDistance = calculateDistance(this, node);

						if(calcDistance < minDistance){
							minDistanceNode = node;
							minDistance = calcDistance;
						}
					}
				}

				neighbors.add(minDistanceNode);
			}
			
			//connects starting room to ending room
			allNodes.get(0).addNeighbor(allNodes.get(allNodes.size() - 1));
		}

		public void addNeighbor(Room node){
			neighbors.add(node);
		}

		public static double calculateDistance(Room a, Room b){
			return Math.hypot(b.x - a.x, b.y - a.y);
		}
	}

	static class RoomGraph{
		ArrayList<Room> nodes = new ArrayList<Room>();
		ArrayList<Room[]> edges = new ArrayList<Room[]>();

		RoomGraph(ArrayList<Room> nodes){
			this.nodes = nodes;

			//gets edges (connected nodes)
			for(int i = 0; i < nodes.size(); i++){
				ArrayList<Room> n = nodes.get(i).neighbors;
				for(int j = 0; j < n.size(); j++){
					Room[] roomArray = {nodes.get(i), n.get(j)};
					edges.add(roomArray);
				}
			}
		}
	}
	
	public static class MapAndStairPoint {
		public Point point;
		public BufferedImage map;
		
		public MapAndStairPoint(Point point, BufferedImage map){
			this.point = point;
			this.map = map;
		}
	}
}



