
public class MapLocation implements Comparable {

	private int x, y;
	private int heuristicCost;
    private int finalCost;
    private MapLocation parent; 
    private int gScore, hScore;
	
	public MapLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.heuristicCost = setManhattanHeuristic();
		this.gScore = 0;
		this.hScore = 0;
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
	
	public int getFScore() {
		return hScore + gScore;
	}
	
	public void setHScore(int hScore) {
		this.hScore = hScore;
	}
	
	public void setGScore(int gScore) {
		this.gScore = gScore;
	}
	
	public int getGScore() {
		return this.gScore;
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
	public String toString() {
		return this.x + ", " + this.y;
	}
	
	@Override
	public boolean equals(Object t) {
		MapLocation other = (MapLocation) t;
		if(this.getX() == other.getX() && this.getY() == other.getY()) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object t) {
        if(!(t instanceof MapLocation))
            return 0;
        MapLocation other = (MapLocation)t;
        if(getFScore() < other.getFScore())
            return -1;
        else if(getFScore() > other.getFScore())
            return 1;
        else return 0;
	}
	
}
