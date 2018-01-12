import java.util.*;

public class Pathfinder {
	
	static MapLocation[][] grid;
	private PriorityQueue<MapLocation> open;
	private boolean[][] closed;
	private static int DIAGONAL_COST = Grid.getDistance(new MapLocation(0,0), new MapLocation(1,1));
    private static final int V_H_COST = Grid.getDistance(new MapLocation(0,0), new MapLocation(0,1)) ;
    
    private void checkAndUpdateCost(MapLocation current, MapLocation t, int cost) {
    	if(t.isObstacle() || closed[t.getX()][t.getY()]) {
    		return; // if this particular location is an obstacle or closed, then skip it.
    	}
    	int t_final_cost = t.getHeuristicCost() + cost; // get the final cost of T.
    	boolean inOpen = inOpen(t); // check if this location is in the open set.
    	if(!inOpen || t_final_cost < t.getFinalCost()) { // if not, or if the final cost is less than the final cost of t
    		t.setFinalCost(t_final_cost); // set the final cost of t equal to t_final_cost
    		t.setParent(current); // set the parent of t equal to the current location.
    		//System.out.println(t.toString() + " -> " + t.getParent().toString());
    		if(!inOpen) {
    			open.add(t);
    		}
    	}
    }
    
    private boolean inOpen(MapLocation mapLocation) {
    	if(open.size()==0 || open == null) {
    		return false;
    	}
    	Iterator<MapLocation> iterator = open.iterator();
    	while(iterator.hasNext()) {
    		MapLocation next = iterator.next();
    		if(next.equals(mapLocation)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private void pathfind() {
        
    	open.add(Grid.getStart());
    	MapLocation current;    	
        
        while(true) { // AStar Loop
        	
        	current = open.poll(); // the current node being evaluated is popped from the PriorityQueue.
        	
        	if(current.isObstacle()) {
        		break; // if the current node being evaluated is an obstacle, then skip it.
        	}
        	
        	closed[current.getX()][current.getY()] = true; // add the current node to closed (evaluated).
        	
        	if(current.isGoal()) {
        		return; //if the current location is the goal, then end.
        	}
        	
        	MapLocation t;
        	
        	if(current.getX()-1 >= 0) { //check if there is a space on the map to the left 1.
        	
        		t = grid[current.getX() - 1][current.getY()];
        		checkAndUpdateCost(current, t, current.getFinalCost()+V_H_COST);
        		
        		if(current.getY()-1 >= 0) { //check if there is a space to the left 1 and up 1.
        			t = grid[current.getX()-1][current.getY()-1];
        			checkAndUpdateCost(current, t, current.getFinalCost()+DIAGONAL_COST);
        		}
        		
        		if(current.getY()+1 <= Grid.getRows()) { //check if there is a space to the left 1 and down 1.
        			t = grid[current.getX()-1][current.getY()+1];
        			checkAndUpdateCost(current, t, current.getFinalCost()+DIAGONAL_COST);
        		}
        		
        	}
        	
        	if(current.getY()-1 >= 0) { // check if there is a space 1 up.
        		t = grid[current.getX()][current.getY()-1];
        		checkAndUpdateCost(current, t, current.getFinalCost()+V_H_COST);
        	}
        	
        	if(current.getY()+1 <= Grid.getRows()) { // check if there is a space 1 down.
        		t = grid[current.getX()][current.getY()+1];
        		checkAndUpdateCost(current, t, current.getFinalCost()+V_H_COST);
        	}
        	
        	if(current.getX()+1 <= Grid.getCols()) { //check if there is a space on the map to the right 1.
        		t = grid[current.getX() + 1][current.getY()];
        		checkAndUpdateCost(current, t, current.getFinalCost()+V_H_COST);
        		
        		if(current.getY()-1 >= 0) { //check if there is a space to the right 1 and up 1.
        			t = grid[current.getX()+1][current.getY()-1];
        			checkAndUpdateCost(current, t, current.getFinalCost()+DIAGONAL_COST);
        		}
        		
        		if(current.getY()+1 <= Grid.getRows()) { //check if there is a space to the right 1 and down 1.
        			t = grid[current.getX()+1][current.getY()+1];
        			checkAndUpdateCost(current, t, current.getFinalCost()+DIAGONAL_COST);
        		}       		
        	}      	
        }	
    }
    
    private void traceBack() {
    	if(closed[Grid.getEnd().getX()][Grid.getEnd().getY()]) {
    		//System.out.println("Path: ");
    		MapLocation current = grid[Grid.getEndXY()[0]][Grid.getEndXY()[1]];
    		//System.out.println(current.toString());
    		while(current.getParent()!=null) {
    			//System.out.print(" -> " + current.getParent().toString());
    			current = current.getParent();
    			addToPath(current);
    		}
    		//System.out.println();
    	}
    	else {
    		System.out.println("No possible path.");
    	}
    }
    
	private void addToPath(MapLocation mapLocation) {
		int[] point = {mapLocation.getX(), mapLocation.getY()};
		Grid.PATHPOINT.add(point);
	}
	
	private void initPathfind() {
		grid = new MapLocation[Grid.getRows()+1][Grid.getCols()+1];
		populateGrid();
    	closed = new boolean[Grid.getRows()+1][Grid.getCols()+1];
    	open = new PriorityQueue<>( (Object o1, Object o2) -> {
    			MapLocation c1 = (MapLocation) o1;
    			MapLocation c2 = (MapLocation) o2;
    			
    			return c1.getFinalCost() < c2.getFinalCost() ? -1:
    					c1.getFinalCost() > c2.getFinalCost() ? 1:0;
    		}   					
		);
    	
	}
	
	private void populateGrid() {
		for(int x = 0; x < Grid.getRows()+1; x++){
	        for(int y = 0; y < Grid.getCols()+1; y++){
	            grid[x][y] = new MapLocation(x, y);
	        }
		}
	}
	
	public void runPathfind() {
		initPathfind();
		pathfind();
		traceBack();
	}
}
