package elukasik.pl.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import elukasik.pl.connection.GameFlow;
import pl.elukasik.model.Board;

/**
 * Main application window
 * 
 * @author plukasik
 *
 */
public class TicTacToeWindow {
	
	public TicTacToeWindow(GameFlow game) {
		
		final JFrame mainFrame = new JFrame("TicTacToe " + game.getUserName());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(400, 400);
		
		mainFrame.setLayout(new BorderLayout());
		
		Board b = new Board();
		TicTacToeBoard panelBoard = new TicTacToeBoard(game);
		mainFrame.add(panelBoard, BorderLayout.CENTER);
		
		panelBoard.drawBoard(b);
		game.setGameBoard(panelBoard);
		
		mainFrame.setVisible(true);
		
		Thread t = new Thread(game);
		t.setDaemon(true);
		t.setName("GameFlow thread");
		t.start();
		
		Thread gameEndThread = new Thread(() -> {
			while (!game.isGameEnd()) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					
				}
			}
			
			SwingUtilities.invokeLater(() -> {
				if (game.getErrMsg() != null) {
					JOptionPane.showMessageDialog(mainFrame, "Couldn't connect to server");
				}
				mainFrame.dispose();
				new TicTacToeMenu();
			});
			
		});
		
		gameEndThread.setDaemon(true);
		gameEndThread.setName("GameEndThread");
		gameEndThread.start();
	}
	
	
}
