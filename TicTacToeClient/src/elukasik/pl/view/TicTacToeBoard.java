package elukasik.pl.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import elukasik.pl.connection.GameFlow;
import elukasik.pl.view.component.ClickableField;
import pl.elukasik.model.Board;

/**
 * Board to play TicTacToe
 * 
 * @author plukasik
 *
 */
public class TicTacToeBoard extends JPanel {

	private static final long serialVersionUID = 1L;

	private ClickableField[][] fields;

	private transient GameFlow flow;

	private JLabel statusLbl;

	public TicTacToeBoard(GameFlow flow) {

		this.flow = flow;

		setLayout(new BorderLayout());

		JPanel boardPanel = new JPanel(new GridLayout(3, 3));

		fields = new ClickableField[3][3];

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				ClickableField lbl = new ClickableField(x, y, x + "x" + y);
				fields[x][y] = lbl;

				final int tmpX = x;
				final int tmpY = y;
				lbl.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						fieldClicked(tmpX, tmpY);
					}
				});
				boardPanel.add(lbl);
			}
		}

		statusLbl = new JLabel("");

		add(boardPanel, BorderLayout.CENTER);
		add(statusLbl, BorderLayout.SOUTH);
	}

	public void drawBoard(Board board) {
		SwingUtilities.invokeLater(() -> {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					
					if (board.getValue(x, y) == 1) {
						fields[x][y].setText("x");
					} else if (board.getValue(x, y) == 2) {
						fields[x][y].setText("o");
					} else {
						fields[x][y].setText("");
					}
				}
			}
		});
	}

	public void fieldClicked(int x, int y) {
		flow.notifyOnClick(x, y);
	}

	public void setStatusText(String text) {
		SwingUtilities.invokeLater(() -> {
			statusLbl.setText(text);
		});
	}

}
