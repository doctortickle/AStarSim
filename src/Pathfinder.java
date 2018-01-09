
public class Pathfinder {
	
	private void addToPath(MapLocation mapLocation) {
		int[] point = {mapLocation.getX(), mapLocation.getY()};
		Grid.PATHPOINT.add(point);
	}
	
}
