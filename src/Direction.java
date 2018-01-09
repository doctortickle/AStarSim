
public class Direction {
	
	private int x, y;
	
	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int[] getXY() {
		int[] xy = {x, y};
		return xy;
	}
}
