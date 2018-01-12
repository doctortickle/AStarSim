import java.util.ArrayList;

public class JPS {

	private MapLocation[][] grid;
	private MapLocation start, end;
	private PQ openList;
	private ArrayList<MapLocation> closedList;
	
	public JPS() {
		assignStartAndEnd();
		populateGrid();		
	}
	
	private void assignStartAndEnd() {
		start = Grid.getStart();
		end = Grid.getEnd();
	}
	
	private void populateGrid() {
		grid = new MapLocation[Grid.getRows()+1][Grid.getCols()+1];
		for(int x = 0; x < Grid.getRows()+1; x++){
	        for(int y = 0; y < Grid.getCols()+1; y++){
	            grid[x][y] = new MapLocation(x, y);
	        }
		}
	}

	public ArrayList<MapLocation> aStarPathFind() {
		closedList = new ArrayList<>();
		openList = new BinaryHeap(start);
		
		while(!openList.isEmpty()) {
			MapLocation curLoc = (MapLocation) openList.pop();
			
			if(curLoc.equals(end)) {
				backTrace(curLoc);
			}
			
			closedList.add(curLoc);
			
			addNeighbors(curLoc, true);
		}
		return null;
	}
	
	private void addNeighbors(MapLocation loc, boolean cutCorners) {
		
		int x = loc.getX(); 
		int y = loc.getY();
		
		for(int dy = -1; dy <=1; dy++) {
			for(int dx = 1; dx >= -1; dx--) {
				MapLocation neighbor = getLoc(x+dx, y+dy);
				if(isValidNeighbor(loc,neighbor)) {
					if(!(dx != 0 && dy != 0) || cutCorners) {
						if(!openList.contains(neighbor)) {
							neighbor.setParent(loc);
							calculateNodeScore(neighbor);
							openList.add(neighbor);
						}
						else if(calculateGScore(loc, neighbor) + loc.getGScore() < neighbor.getGScore()) {
							neighbor.setParent(loc);
							calculateNodeScore(neighbor);
						}
					}
				}
			}			
		}		
	}
	
	private void calculateNodeScore(MapLocation loc) {
		int manhattan = Math.abs(end.getX() - loc.getX()) * 10 + Math.abs(end.getY() - loc.getY()) * 10;
		loc.setHScore(manhattan);
		MapLocation parent = loc.getParent();
		loc.setGScore(parent.getGScore() + calculateGScore(loc, parent));
		
	}
	
	private int calculateGScore(MapLocation newLoc, MapLocation oldLoc) {
		int dx = newLoc.getX() - oldLoc.getX();
        int dy = newLoc.getY() - oldLoc.getY();
        
            if(dx == 0 || dy == 0) {
            	return Math.abs(10 * Math.max(dx, dy));
            }              
            else {
            	return Math.abs(14 * Math.max(dx, dy));
            }           
	}
	
	public void jumpPointSearch() {
		
		closedList = new ArrayList<>();
		openList = new BinaryHeap(start);
		
		while(!openList.isEmpty()) {
			MapLocation curLoc = (MapLocation) openList.pop();
			if(curLoc.equals(end)) {
				backTrace(curLoc);
			}
			identifySuccessors(curLoc);
			closedList.add(curLoc);
		}
	}
	
	private void identifySuccessors(MapLocation curLoc) {
		
        for(int dx = -1; dx <= 1; dx++) 
        {
            for(int dy = -1; dy <= 1; dy++)
            {
                if(dx == 0 && dy == 0)
                    continue;

                if(isValidNeighbor(curLoc, getLoc(curLoc.getX() + dx, curLoc.getY() + dy)))
                {
                    MapLocation jumpLoc = jump(curLoc, dx, dy);
                    if(jumpLoc != null)
                    {                       
                        if(!openList.contains(jumpLoc) && !closedList.contains(jumpLoc))
                        {
                            jumpLoc.setParent(curLoc); //Set its parent so we can find our way back later
                            calculateNodeScore(jumpLoc);   //Calculate its score to pull it from the open list later
                            openList.add(jumpLoc);     // Add it to the open list for continuing the path
                        }
                    }
                }
                
            }
        }
		
	}
	
	private MapLocation jump(MapLocation curLoc, int dx, int dy) {
		
		int nextX = curLoc.getX() + dx;
		int nextY = curLoc.getY() + dy;
		
		MapLocation nextLoc = getLoc(nextX, nextY);
		
		if(nextLoc == null || nextLoc.isObstacle()) {
			return null;
		}
		
		if(nextLoc.equals(end)) {
			return nextLoc;
		}
		
		if(dx != 0 && dy !=0) { 

			if(getLoc(nextX - dx, nextY) != null && getLoc(nextX - dx, nextY + dy) != null)
                if(getLoc(nextX - dx, nextY).isObstacle() && !getLoc(nextX - dx, nextY + dy).isObstacle())
                    return nextLoc;
		

            if(getLoc(nextX, nextY - dy) != null && getLoc(nextX + dx, nextY - dy) != null)
                if(getLoc(nextX, nextY - dy).isObstacle() && !getLoc(nextX + dx, nextY - dy).isObstacle())
                    return nextLoc;
	
   
            if(jump(nextLoc, dx, 0) != null || jump(nextLoc, 0, dy) != null)
                return nextLoc; 
		}
		
		else {
			
			if(dx != 0) {
				
				if(getLoc(nextX + dx, nextY) != null && getLoc(nextX, nextY +1) != null) {
					if(!getLoc(nextX + dx, nextY).isObstacle() && getLoc(nextX, nextY + 1).isObstacle()) {
						if(!getLoc(nextX + dx, nextY + 1).isObstacle()) {
							return nextLoc;
						}
					}
				}	
				
				if(getLoc(nextX + dx, nextY) != null && getLoc(nextX, nextY - 1) != null) {
					if(!getLoc(nextX + dx, nextY).isObstacle() && getLoc(nextX, nextY - 1).isObstacle()) {
						if(!getLoc(nextX + dx, nextY - 1).isObstacle()) {
							return nextLoc;
						}
					}
				}
			}
			
			else {
				
				if(getLoc(nextX, nextY + dy) != null && getLoc(nextX +1, nextY) != null) {
					if(!getLoc(nextX, nextY + dy).isObstacle() && getLoc(nextX +1, nextY).isObstacle()) {
						if(!getLoc(nextX + 1, nextY + dy).isObstacle()) {
							return nextLoc;
						}
					}
				}
				
				if(getLoc(nextX, nextY + dy) != null && getLoc(nextX - 1, nextY) != null) {
					if(!getLoc(nextX, nextY + dy).isObstacle() && getLoc(nextX - 1, nextY).isObstacle()) {
						if(!getLoc(nextX -1, nextY + dy).isObstacle()) {
							return nextLoc;
						}
					}
				}
			}	
		}
		return jump(nextLoc, dx, dy);
	}
	
	private void backTrace(MapLocation theLoc) {
		
		MapLocation parent = theLoc;
		while(parent != null) {;
			addToPath(parent);
			parent = (MapLocation) parent.getParent();
		}
	}
	
	
	private boolean isValidNeighbor(MapLocation mapLoc, MapLocation neighbor) {
		return neighbor != null && !neighbor.isObstacle() && !closedList.contains(neighbor)
				&& !neighbor.equals(mapLoc);
	}
	
	private void addToPath(MapLocation mapLocation) {
		int[] point = {mapLocation.getX(), mapLocation.getY()};
		Grid.PATHPOINT.add(point);
	}
	
	private MapLocation getLoc(int x, int y) {
		 if(x > Grid.getCols() || x < 0 || y > Grid.getRows() || y < 0)
	            return null;
	     return this.grid[x][y];
	}
}
