import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameWindow extends JPanel {
	
	private final Pathfinder pathfinder = new Pathfinder();
	private final JPS jps = new JPS();
	private static final Color UNOCCUPIED = new Color(255, 255, 255);
	private static final Color OBSTACLE = new Color(0, 0, 0);
	private static final Color PLAYER = new Color(51, 51, 204);
	private static final Color PATH = new Color(255, 0 ,0);
	private static final Color GOAL = new Color(255, 255, 0);
	
	private GameWindow() {
		int preferredWidth = Grid.getCols() * Grid.getGridSizePixels();
		int preferredHeight = Grid.getRows() * Grid.getGridSizePixels();
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        int rectWidth = getWidth() / Grid.getCols();
        int rectHeight = getHeight() / Grid.getRows();
        
        //pathfinder.runPathfind();
        jps.jumpPointSearch();

        for (int i = 0; i < Grid.getRows(); i++) {
            for (int j = 0; j < Grid.getCols(); j++) {
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
                } else if (checkPath(i,j)) {
                		g.setColor(PATH);
                		g.fillRect(x, y,rectWidth-1, rectHeight-1);
                }
                else {
                    g.setColor(UNOCCUPIED);
                    g.fillRect(x, y,rectWidth-1, rectHeight-1);
                }               
            }
        }
    }
	
	private static boolean checkObstacle(int x, int y) {
		for (int[] point : Grid.getObstacles()) {
			if (point[0] == x && point[1] == y) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkStart(int x, int y) {
		if (Grid.getStart().getX() == x && Grid.getStart().getY() == y) {
			return true;
		}
		return false;
	}
	
	private static boolean checkEnd(int x, int y) {
		if (Grid.getEnd().getX() == x && Grid.getEnd().getY() == y) {
			return true;
		}
		return false;
	}
	
	private static boolean checkPath(int x, int y) {
		for (int[] point : Grid.getPATHPOINT()) {
			if (point[0] == x && point[1] == y) {
				return true;
			}
		}
		return false;
	}
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Game");
                GameWindow grid = new GameWindow();
                frame.add(grid);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
