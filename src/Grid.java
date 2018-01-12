import java.util.ArrayList;

public class Grid {
	
	private static final int NUM_ROWS = 20;
	private static final int NUM_COLS = 20;
	private static final int GRID_SIZE_PIXELS = 10;
	private static final int[][] OBSTACLES = {{5, 5}, {6, 6}, {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10},
												{6,5}, {7,5}, {8,5}, {9,5}, {10, 5}, {18,18}, {18,19},
												{18,20}, {20, 10}, {19, 10}, {18, 10}, {17, 10}};
	private static final int START_X = 19;
	private static final int START_Y = 19;
	private static final int END_X = 1;
	private static final int END_Y = 2;
	private static final MapLocation START = new MapLocation(START_X, START_Y);
	private static final MapLocation END = new MapLocation(END_X, END_Y);
	public static ArrayList<int[]> PATHPOINT = new ArrayList<int[]>();
	
	public static int getCols() {
		return NUM_COLS;
	}
	
	public static int getRows() {
		return NUM_ROWS;
	}
    
    public static int getGridSizePixels() {
		return GRID_SIZE_PIXELS;
	}

	private Direction getDirection(int[] one, int[] two) {
    	int oneX = one[0];
    	int oneY = one[1];
    	int twoX = two[0];
    	int twoY = two[1];
    	return new Direction((oneX - twoX), (oneY - twoY));
    
    }

	private static int getDistance(int[] one, int[] two) {
		int oneX = one[0];
	    	int oneY = one[1];
	    	int twoX = two[0];
	    	int twoY = two[1];
	    	if(oneX == twoX || oneY == twoY) {
	    		if(oneX == twoX) {
	    			return Math.abs(oneY - twoY);
	    		}
	    		else {
	    			return Math.abs(oneX - twoX);
	    		}
	    	}
	    	else {
	    		return (int) (Math.pow((double)Math.abs(oneX-twoX), 2.0) + Math.pow((double)Math.abs(oneY-twoY), 2.0));
	    	}  	
	}
	
    private static boolean checkObstacle(int x, int y) {
		for (int[] point : OBSTACLES) {
			if (point[0] == x && point[1] == y) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkStart(int x, int y) {
		if (START.getX() == x && START.getY() == y) {
			return true;
		}
		return false;
	}
	
	private static boolean checkEnd(int x, int y) {
		if (END.getX() == x && END.getY() == y) {
			return true;
		}
		return false;
	}
	
	private static boolean checkPath(int x, int y) {
		for (int[] point : PATHPOINT) {
			if (point[0] == x && point[1] == y) {
				return true;
			}
		}
		return false;
	}
	
	public static int getDistance(MapLocation one, MapLocation two) {
		int[] mapOne = {one.getX(), one.getY()};
		int[] mapTwo = {two.getX(), two.getY()};
		
		return getDistance(mapOne, mapTwo);
	}
	
	public Direction getDirection(MapLocation one, MapLocation two) {
		int[] mapOne = {one.getX(), one.getY()};
		int[] mapTwo = {two.getX(), two.getY()};
		return getDirection(mapOne, mapTwo);
	}
    
    public static boolean checkObstacle(MapLocation mapLocation) {
		return checkObstacle(mapLocation.getX(), mapLocation.getY());
	}
	
	public static boolean checkStart(MapLocation mapLocation) {
		return checkStart(mapLocation.getX(), mapLocation.getY());
	}
	
	public static boolean checkEnd(MapLocation mapLocation) {
		return checkEnd(mapLocation.getX(), mapLocation.getY());
	}
	
	public static boolean checkPath(MapLocation mapLocation) {
		return checkPath(mapLocation.getX(), mapLocation.getY());
	}
	
	public static MapLocation getStart() {
		return START;
	}
	
	public static MapLocation getEnd() {
		return END;
	}
	
	public static int[] getStartXY() {
		int[] startXY = {START_X, START_Y};
		return startXY;
	}
	
	public static int[] getEndXY() {
		int[] startXY = {END_X, END_Y};
		return startXY;
	}

	public static int[][] getObstacles() {
		return OBSTACLES;
	}

	public static ArrayList<int[]> getPATHPOINT() {
		return PATHPOINT;
	}
	
}
