package elukasik.pl.game;

public class Board {
	
	private Player[][] board;
	
	public Board() {
		board = new Player[3][3];
		setAllBoardValues(Player.EMPTY);
	}
	
	
	public void setAllBoardValues(Player player) {
		for (int x = 0; x < 3; x++){
			for (int y = 0; y < 3; y++) {
				board[x][y] = player;
			}
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int x = 0; x < 3; x++){
			for (int y = 0; y < 3; y++) {
				sb.append(board[x][y]).append(" | ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public void fieldClicked(int x, int y) {
		board[x][y] = Player.PLAYER;
	}
	
	public Player getValue(int x, int y) {
		Player val = board[x][y];
		return val;
	}
	
	public static Player checkIsEnd(Board b) {
		
		Player p = Player.PLAYER;
		if (b.getValue(0, 0) != Player.EMPTY && b.getValue(0, 0) == b.getValue(0, 1) && b.getValue(0, 1) ==  b.getValue(0, 2)) {
			
		} else if (b.getValue(1, 0) != Player.EMPTY  && b.getValue(1, 0) == b.getValue(1, 1) && b.getValue(1, 1) ==  b.getValue(1, 2)) {
			
		} else if (b.getValue(2, 0) != Player.EMPTY  && b.getValue(2, 0) == b.getValue(2, 1) && b.getValue(2, 1) ==  b.getValue(2, 2)) {
			
		} else if (b.getValue(0, 0) != Player.EMPTY  && b.getValue(0, 0) == b.getValue(1, 0) && b.getValue(1, 0) ==  b.getValue(2, 0)) {
			
		} else if (b.getValue(0, 1) != Player.EMPTY  && b.getValue(0, 1) == b.getValue(1, 1) && b.getValue(1, 1) ==  b.getValue(2, 1)) {
			
		} else if (b.getValue(0, 2) != Player.EMPTY  && b.getValue(0, 2) == b.getValue(1, 2) && b.getValue(1, 2) ==  b.getValue(2, 2)) {
			
		} else if (b.getValue(0, 0) != Player.EMPTY  && b.getValue(0, 0) == b.getValue(1, 1) && b.getValue(1, 1) == b.getValue(2, 2)) {
			
		} else if (b.getValue(0, 2) != Player.EMPTY  && b.getValue(0, 2) == b.getValue(1, 1) && b.getValue(1, 1) == b.getValue(2, 0)) {
			
		} else {
			p = null;
		}
		
		
		return p;
	}
	
	
}
