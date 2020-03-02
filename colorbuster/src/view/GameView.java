/**
 * 
 */
package view;

import javax.swing.JFrame;
import java.awt.*;

//import view.ScoreView;
//import view.ButtonView;
import view.BoardView;
import java.awt.event.*;

/**
 * @author Dayna Robinson
 *
 */
public class GameView extends JFrame {
	// Create the HUD Panel
	// Create the Board
	
	
	private static final long serialVersionUID = 1L;
	private ScoreView scoreView;
	private ButtonView buttonView;
	private BoardView boardView;
	
	private int score;
	private int minMatches = 3;
	// Setting default to 8x8
	
	int rows = 8, cols = 8;
	int width, height;
	

	ActionListener newGameListener;
	ActionListener tileTouchedListener;
	ActionListener minMatchesListener;
	
	/**
	 * @param title
	 * @throws HeadlessException
	 */
	
	
	public GameView(String title, int rows, int cols, ActionListener minMatchesListener, ActionListener newGameListener, ActionListener tileTouched) throws HeadlessException {
		super(title);
		setResizable(false);
		
		width = 400;
		height = 500;
		score = 0;
		this.rows = rows;
		this.cols = cols;
		this.newGameListener = newGameListener;
		this.tileTouchedListener = tileTouched;
		
		// Set up some reasonable sizes for the gameview
		
		setBounds(100,50,width, height);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		scoreView = new ScoreView();
    	add(scoreView, BorderLayout.NORTH);
		
    	buttonView = new ButtonView(newGameListener, minMatchesListener);
    	add(buttonView, BorderLayout.SOUTH);
		
		boardView = new BoardView(rows,cols,minMatches, tileTouchedListener);
		add(boardView, BorderLayout.CENTER);
		
		setVisible(true);
		System.out.println(minMatches);
	}
	
	public int getScore() {
		return score;
	}
	
	// Delegate to boardView
	public boolean isMoveAvailable() {
		if (boardView.isMoveAvailable() == true)
			return true;
		else
            return false;
		// TODO you need to delegate this behavior to the boardView
	}
	
	// Call this method to start a new Game
	
	public void newGame(int minMatches) {
		// Recreate some components and update the GUI.
		
		Container c = getContentPane();
		c.remove(boardView);
		c.invalidate();
		pack();

		boardView = null;
		score = 0;
		scoreView.updateScore(score);
		this.minMatches = minMatches;
		boardView = new BoardView(rows,cols,minMatches, tileTouchedListener);
		
		add(boardView, BorderLayout.CENTER);
		pack();
		revalidate();
		setBounds(100,50,width, height);
		
		System.out.println(boardView);  // debug
	}
	
	public void processTouchedTile(TileView tv) {
		// TODO
		// You need to implement this method. It is called when a tileview is touched
		int scoreThisMove = boardView.processTouchedTile(tv);

		score = score + scoreThisMove;
		scoreView.updateScore(score);
		System.out.println("GameView == processing tile touch");
		
		System.out.println(boardView); // debug
		repaint();
	}
	
	public void updateMinMatches(int minMatches) {
		this.minMatches = minMatches;
		boardView.setMinMatches(minMatches);
	}

}
