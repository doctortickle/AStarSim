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
				MapLocation neighbor = grid[x+dx][y+dy];
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
                
                if(curLoc.getX() + dx < 1 || curLoc.getY() + dy < 1 || curLoc.getX() + dx > Grid.getRows() || curLoc.getY() + dy > Grid.getCols())  {
                		continue;
                }
                System.out.println(curLoc.getX() + ", " + curLoc.getY());
                if(isValidNeighbor(curLoc, grid[curLoc.getX() + dx][curLoc.getY() + dy]))
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
		if(nextX > Grid.getCols() || nextY > Grid.getRows()) {
			return null;
		}

		MapLocation nextLoc = grid[nextX][nextY];
		
		if(nextLoc == null || nextLoc.isObstacle()) {
			return null;
		}
		
		if(nextLoc.equals(end)) {
			return nextLoc;
		}
		
		if(dx != 0 && dy !=0) { 
			
			if(!(nextX - dx < 0) && !(nextY + dy > Grid.getRows())) {
				if(grid[nextX - dx] [nextY] != null && grid[nextX - dx][nextY + dy] != null)
	                if(grid[nextX - dx][nextY].isObstacle() && !grid[nextX - dx][nextY + dy].isObstacle())
	                    return nextLoc;
			}
			
			if(!(nextY - dy < 0) && !(nextX + dx > Grid.getCols())) {
	            if(grid[nextX] [nextY - dy] != null && grid[nextX + dx][nextY - dy] != null)
	                if(grid[nextX][nextY - dy].isObstacle() && !grid[nextX + dx][nextY - dy].isObstacle())
	                    return nextLoc;
			}
   
            if(jump(nextLoc, dx, 0) != null || jump(nextLoc, 0, dy) != null)
                return nextLoc; 
		}
		
		else {
			
			if(dx != 0) {
				
				if(!(nextY + 1 > Grid.getRows()) && !(nextX + dx > Grid.getCols())) {
					if(!grid[nextX + dx][nextY].isObstacle() && grid[nextX][nextY + 1].isObstacle()) {
						if(!grid[nextX + dx][nextY + 1].isObstacle()) {
							return nextLoc;
						}
					}
				}
				
				if(!(nextY - 1 < 0) && !(nextX + dx > Grid.getCols())) {
					if(!grid[nextX + dx][nextY].isObstacle() && grid[nextX][nextY - 1].isObstacle()) {
						if(!grid[nextX + dx][nextY - 1].isObstacle()) {
							return nextLoc;
						}
					}
				}
							
			}
			
			else {
				if(!(nextY + dy > Grid.getCols()) && !(nextX + 1 > Grid.getCols())) {
					if(!grid[nextX][nextY + dy].isObstacle() && grid[nextX +1][nextY].isObstacle()) {
						if(!grid[nextX + 1][nextY + dy].isObstacle()) {
							return nextLoc;
						}
					}
				}
				
				
				if(!(nextY + dy > Grid.getCols()) && !(nextX - 1 < 0)) {
					if(!grid[nextX][nextY + dy].isObstacle() && grid[nextX -1][nextY].isObstacle()) {
						if(!grid[nextX -1][nextY + dy].isObstacle()) {
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
	
	private boolean DxDyInBounds(MapLocation curLoc, int dx, int dy) {
		if(curLoc.getX() + dx < 1 || curLoc.getY() + dy < 1 || curLoc.getX() + dx > Grid.getRows() || curLoc.getY() + dy > Grid.getCols())  {
    			return false;
		}
		return true;
	}
	
}
