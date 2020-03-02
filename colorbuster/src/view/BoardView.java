/**
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Board;
import model.Tile;
import view.TileView;

/**
 * @author Dayna Robinson
 * 
 * View class over the board model.
 *
 *
 */
public class BoardView extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private Board b;
	private int rows;
	private int cols;
	private int tileSize;
	private ActionListener listener;
	private int spacer;
	private int minMatches;
	
	private TileView [][] tileGrid;
	
	// Create  BoardView with rows and cols and lis is the actionListener for all the TileViews
	
	public BoardView(int rows, int cols, int minMatches, ActionListener lis) {
	    spacer = 4;
		listener = lis;
		Dimension s = getPreferredSize();
		System.out.println("BoardView: " + s);
		this.rows = rows;
		this.cols = cols;	
		this.minMatches = minMatches;
		int totalSpace = (cols+1) * spacer;
		System.out.println("totalspace : " + totalSpace);
		
		tileSize = (s.width-totalSpace) / cols;
		System.out.println("TileSize : " + tileSize);
		
		// I'm not using a layout manager here since I'm using XY to layout the TileViews
		// Quick and simple
		
		setLayout(null);
		tileGrid = new TileView[rows][cols];
		b = new Board(rows,cols, minMatches);
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				TileView tv;
				tv = new TileView(b.tileAt(row, col));
				add(tv);
				tileGrid[row][col] = tv;
			
				tv.setBounds((tileSize * col + spacer), (tileSize * ((rows-1)-row) + spacer),
							             tileSize, tileSize);
				tv.setPosition(tileSize * col + spacer, (tileSize * ((rows-1)-row) + spacer));
				
				tv.addListener(listener);

				
			}
		}
		
	}
	
// Call this method whenever you want to update the boardView on the display from the current status of the board
	
	public void updateBoardViewFromBoard() {
		System.out.println("In updateBoardViewFromBoard...");
		TileView tv;
		removeAll();
		tileGrid = new TileView[rows][cols];
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				if (b.hasTileAt(row,col)) {
					 tv = new TileView(b.tileAt(row, col));
				}
				else  {
					tv = new TileView(row,col);
					
				}
				add(tv);
				tileGrid[row][col] = tv;
			
			
				tv.setBounds((tileSize * col + spacer), (tileSize * ((rows-1)-row) + spacer),
							             tileSize, tileSize);
				tv.setPosition(tileSize * col + spacer, (tileSize * ((rows-1)-row) + spacer));
				
				tv.addListener(listener);

				
			}
		}
		
		
	}
	

	// returns the score which would be however you decide to score matched tiles.
	// You don't have to handle the score this way -- do it however you decide works best for you
	
	
	public int processTouchedTile(TileView tv) {
		// TODO
		// You implement this method.
		// locate the neighbors, collapse, and then call updateBoardViewFromBoard() to update the display
		
		// Don' forget to call updateBoardViewFromBoard() at the end of this method
		HashSet<Tile> matches = new HashSet<Tile>();
		b.locateNeighbors(tv.getRow(), tv.getCol(), tv.getColor(), matches);
		System.out.println(minMatches);
		if (matches.size() >= minMatches) {
			int scoreThisMove = matches.size() * 100;//EDIT: changed score system to use the custom points
			System.out.println(matches.toString());
			b.removeMatchingTiles(matches);
			b.collapseColumns();		
			updateBoardViewFromBoard();
			System.out.println(b);
			System.out.println(matches.size() * 100 + " From BoardView");
			return scoreThisMove; // you figure out the score
		}
		else {
			JOptionPane.showMessageDialog(getParent(), "The minimum number of matches is " + minMatches);
			return 0;
		}
	}
	
	
	public String toString() {
		return b.toString();
		
		// Just call b.toString().
		// The commented code below you can use to verify that the BoardView is actually synchronized with the Board
		
//		String v = "";
//		for (int row=rows-1;row>=0;row--) {
//			for (int col=0;col<cols;col++)  {
//				if (tileGrid[row][col] != null)
//					v += tileGrid[row][col].toString();
//				else
//					v += "[-----]";
//			}
//			v += "\n";
//		}	return v + b.toString();
	}
	

	public boolean isMoveAvailable() {
		return b.isMoveAvailable();
	}
	
	// set the default size of the boardview to a reasonable size
	public Dimension getPreferredSize() {
		return new Dimension(400,400);
	}

	public void setMinMatches(int minMatches) {
		this.minMatches = minMatches;
		
	}
}
