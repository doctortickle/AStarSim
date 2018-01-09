
public class MapLocation {

	private int x, y;
	private int heuristicCost;
    private int finalCost;
    private MapLocation parent; 
	
	public MapLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.heuristicCost = setManhattanHeuristic();
	}
	
	private int setManhattanHeuristic() {
		return Math.abs(this.x - Grid.getEndXY()[0]) + Math.abs(this.y - Grid.getEndXY()[1]);

	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
		
	public int getHeuristicCost() {
		return heuristicCost;
	}

	public int getFinalCost() {
		return finalCost;
	}

	public MapLocation getParent() {
		return parent;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setFinalCost(int f) {
		this.finalCost = f;
	}
	
	public void setParent(MapLocation parent) {
		this.parent = parent;
	}

	public boolean isObstacle() {
		return Grid.checkObstacle(this);
	}
	
	public boolean isGoal() {
		return Grid.checkEnd(this);
	}
	
    @Override
    public String toString(){
        return "["+this.x+", "+this.y+"]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapLocation other = (MapLocation) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
