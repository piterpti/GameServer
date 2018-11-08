package elukasik.pl.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import elukasik.pl.connection.GameFlow;

public class TicTacToeMenu {
	
	public TicTacToeMenu() {
		
		JFrame menuFrame = new JFrame("TicTacToe");
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel menuPanel = new JPanel(new GridLayout(3, 1));
		
		final JTextField nameTf = new JTextField("Name");
		menuPanel.add(nameTf);
		
		JButton startGameBtn = new JButton("Start game");
		startGameBtn.addActionListener(e -> {
			String name = nameTf.getText();
			GameFlow game = new GameFlow(name);
			new TicTacToeWindow(game);
			menuFrame.dispose();
		});
		menuPanel.add(startGameBtn);
		
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.addActionListener(e -> 	System.exit(0));
		
		menuPanel.add(exitBtn);
		
		menuFrame.add(menuPanel);
		
		menuFrame.setSize(400, 400);
		menuFrame.setVisible(true);
	}

}
