import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Grid extends JPanel {
	
	private static final int NUM_ROWS = 20;
	private static final int NUM_COLS = 20;
	private static final int GRID_SIZE_PIXELS = 10;
	private static final int[][] OBSTACLES = {{5, 5}, {6, 6}, {5, 6}, {5, 7}, {5, 8}, {5, 9}, {5, 10},
												{6,5}, {7,5}, {8,5}, {9,5}, {10, 5}};
	private static final int[] START = {1,1};
	private static final int[] END = {19,19};
	public static ArrayList<int[]> PATHPOINT = new ArrayList<int[]>();
	
	private static final Color UNOCCUPIED = new Color(255, 255, 255);
	private static final Color OBSTACLE = new Color(0, 0, 0);
	private static final Color PLAYER = new Color(51, 51, 204);
	private static final Color PATH = new Color(255, 0 ,0);
	private static final Color GOAL = new Color(255, 255, 0);
	
	private Grid() {
		int preferredWidth = NUM_COLS * GRID_SIZE_PIXELS;
		int preferredHeight = NUM_ROWS * GRID_SIZE_PIXELS;
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
	}
	
	public int getCols() {
		return NUM_COLS;
	}
	
	public int getRows() {
		return NUM_ROWS;
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        int rectWidth = getWidth() / NUM_COLS;
        int rectHeight = getHeight() / NUM_ROWS;


        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int x = i * rectWidth;
                int y = j * rectHeight;
                g.setColor(Color.BLACK);
                g.fillRect(x, y, rectWidth, rectHeight);
                if(checkObstacle(i,j)) {
                		g.setColor(OBSTACLE);
                    g.fillRect(x, y,rectWidth-1, rectHeight-1);
                }
                else if (checkStart(i,j)) {
            			g.setColor(PLAYER);
            			g.fillRect(x, y,rectWidth-1, rectHeight-1);
                } else if (checkEnd(i,j)) {
                		g.setColor(GOAL);
        				g.fillRect(x, y,rectWidth-1, rectHeight-1);
                } else {
                    g.setColor(UNOCCUPIED);
                    g.fillRect(x, y,rectWidth-1, rectHeight-1);
                }               
            }
        }
    }
    
    private boolean checkObstacle(int x, int y) {
    		for (int[] point : OBSTACLES) {
    			if (point[0] == x && point[1] == y) {
    				return true;
    			}
    		}
    		return false;
    }
    
    private boolean checkStart(int x, int y) {
		if (START[0] == x && START[1] == y) {
			return true;
		}
		return false;
    }
    
    private boolean checkEnd(int x, int y) {
		if (END[0] == x && END[1] == y) {
			return true;
		}
		return false;
    }
    
    private boolean checkPath(int x, int y) {
		for (int[] point : PATHPOINT) {
			if (point[0] == x && point[1] == y) {
				return true;
			}
		}
		return false;
    }
    
    public boolean checkObstacle(MapLocation mapLocation) {
		return checkObstacle(mapLocation.getX(), mapLocation.getY());
	}
	
	public boolean checkStart(MapLocation mapLocation) {
		return checkStart(mapLocation.getX(), mapLocation.getY());
	}
	
	public boolean checkEnd(MapLocation mapLocation) {
		return checkEnd(mapLocation.getX(), mapLocation.getY());
	}
	
	public boolean checkPath(MapLocation mapLocation) {
		return checkPath(mapLocation.getX(), mapLocation.getY());
	}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Game");
                Grid grid = new Grid();
                frame.add(grid);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
	
}
