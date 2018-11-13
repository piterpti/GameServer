package pl.elukasik.tollkit;

import pl.elukasik.dao.model.GameState;
import pl.elukasik.model.Board;

/**
 * Toolkit for various game operations
 * 
 * @author plukasik
 *
 */
public class GameToolkit {
	
	private GameToolkit() {
		
	}

	/**
	 * Check is the game is end and return winner id
	 * 
	 * @param b
	 * @return
	 */
	public static int checkIsEnd(Board b) {

		int p = -1;
		if (b.getValue(0, 0) != 0 && b.getValue(0, 0) == b.getValue(0, 1) && b.getValue(0, 1) == b.getValue(0, 2)) {
			p = b.getValue(0, 0);
		} else if (b.getValue(1, 0) != 0 && b.getValue(1, 0) == b.getValue(1, 1)
				&& b.getValue(1, 1) == b.getValue(1, 2)) {
			p = b.getValue(1, 0);
		} else if (b.getValue(2, 0) != 0 && b.getValue(2, 0) == b.getValue(2, 1)
				&& b.getValue(2, 1) == b.getValue(2, 2)) {
			p = b.getValue(2, 0);
		} else if (b.getValue(0, 0) != 0 && b.getValue(0, 0) == b.getValue(1, 0)
				&& b.getValue(1, 0) == b.getValue(2, 0)) {
			p = b.getValue(0, 0);
		} else if (b.getValue(0, 1) != 0 && b.getValue(0, 1) == b.getValue(1, 1)
				&& b.getValue(1, 1) == b.getValue(2, 1)) {
			p = b.getValue(0, 1);
		} else if (b.getValue(0, 2) != 0 && b.getValue(0, 2) == b.getValue(1, 2)
				&& b.getValue(1, 2) == b.getValue(2, 2)) {
			p = b.getValue(0, 2);
		} else if (b.getValue(0, 0) != 0 && b.getValue(0, 0) == b.getValue(1, 1)
				&& b.getValue(1, 1) == b.getValue(2, 2)) {
			p = b.getValue(0, 0);
		} else if (b.getValue(0, 2) != 0 && b.getValue(0, 2) == b.getValue(1, 1)
				&& b.getValue(1, 1) == b.getValue(2, 0)) {
			p = b.getValue(0, 2);
		} else {
			p = 0;
		}

		return p;
	}

	public static GameState convertBoard2GameState(Board board, long gameId, boolean end) {

		GameState gs = new GameState();

		int[][] fields = board.getBoard();

		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				sb.append(fields[y][x]);
			}
		}

		gs.setGameState(sb.toString());
		gs.setId(gameId);
		gs.setEnd(end);

		return gs;
	}

	public static Board convertGameState2Board(GameState gs) {

		if (gs.getGameState().length() != 9) {
			throw new IllegalArgumentException("Game state should have 9 char length!");
		}

		Board b = new Board();

		int idx = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				b.fieldClicked(x, y, Character.getNumericValue(gs.getGameState().charAt(idx++)));
			}
		}

		return b;
	}

}
