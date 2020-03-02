/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import view.GameView;
import view.TileView;

/**
 * @author Dayna Robinson
 *
 */
public class GameController {
	// These aren't used, but could be depending on your game and what you want to do
	private int score;
	// private int gameStatus;
	// private int rows;
	// private int cols;
	private int minMatches = 3;
	// private int moveNumber = 0;
	
	private GameView gameView = new GameView("Dayna's Color Buster",6,6, new MinMatchesListener(),new NewGameListener(), new TileTouchedListener());
	
	

	/**
	 * Create the GameView and pass in the appropriate listeners
	 */
	public GameController() {
		super();		
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				gameView.setVisible(true);
			}
		});
		
	}
	
	// Listener used to process touches on TileView
	
	class TileTouchedListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent event) {
			TileView tv = (TileView) event.getSource();
			System.out.println("Tile touched..." + tv.toString());
			
			gameView.processTouchedTile(tv);
			
			// If no move is available display a message
			
			if (!gameView.isMoveAvailable()) {
				JOptionPane.showMessageDialog(gameView,
					    "No more moves...Your final score is " + gameView.getScore());
			}
		}
		
	}
	
	// Listener used to process click on New Game Button
	
	class NewGameListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent event) {
			System.out.println("Starting new game...");
			// System.out.println("Not yet implemented..");
			gameView.newGame(minMatches);
			
			// TODO
			// You implement this method. Delegate to GameView!
		}
		
	}
	
	class MinMatchesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			System.out.print("Changing level...");
			JComboBox cb = (JComboBox) event.getSource();
			String level = (String) cb.getSelectedItem();
			minMatches = Integer.valueOf(level);
			// System.out.println(minMatches);
			gameView.updateMinMatches(minMatches);
			gameView.newGame(minMatches);
		}
		
	}
	
}
