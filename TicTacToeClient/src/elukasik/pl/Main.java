package elukasik.pl;

import javax.swing.SwingUtilities;

import elukasik.pl.view.TicTacToeMenu;

/**
 * Main application class
 * 
 * @author plukasik
 *
 */
public class Main {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			new TicTacToeMenu();
		});
	}

}
