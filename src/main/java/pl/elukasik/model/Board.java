package pl.elukasik.model;

import java.io.Serializable;

public class Board implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private int[][] board;
	
	public Board() {
		board = new int[3][3];
		setAllBoardValues(0);
	}
	
	
	public void setAllBoardValues(int player) {
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
	
	public void fieldClicked(int x, int y, int playerId) {
		board[x][y] = playerId;
	}
	
	public int getValue(int x, int y) {
		return board[x][y];
	}
	
	public static int checkIsEnd(Board b) {
		
		int p = -1;
		if (b.getValue(0, 0) != 0 && b.getValue(0, 0) == b.getValue(0, 1) && b.getValue(0, 1) ==  b.getValue(0, 2)) {
			p = b.getValue(0, 0);
		} else if (b.getValue(1, 0) != 0  && b.getValue(1, 0) == b.getValue(1, 1) && b.getValue(1, 1) ==  b.getValue(1, 2)) {
			p = b.getValue(1, 0);
		} else if (b.getValue(2, 0) != 0  && b.getValue(2, 0) == b.getValue(2, 1) && b.getValue(2, 1) ==  b.getValue(2, 2)) {
			p = b.getValue(2, 0);
		} else if (b.getValue(0, 0) != 0  && b.getValue(0, 0) == b.getValue(1, 0) && b.getValue(1, 0) ==  b.getValue(2, 0)) {
			p = b.getValue(0, 0);
		} else if (b.getValue(0, 1) != 0  && b.getValue(0, 1) == b.getValue(1, 1) && b.getValue(1, 1) ==  b.getValue(2, 1)) {
			p = b.getValue(0, 1);
		} else if (b.getValue(0, 2) != 0  && b.getValue(0, 2) == b.getValue(1, 2) && b.getValue(1, 2) ==  b.getValue(2, 2)) {
			p = b.getValue(0, 2);
		} else if (b.getValue(0, 0) != 0  && b.getValue(0, 0) == b.getValue(1, 1) && b.getValue(1, 1) == b.getValue(2, 2)) {
			p = b.getValue(0, 0);
		} else if (b.getValue(0, 2) != 0 && b.getValue(0, 2) == b.getValue(1, 1) && b.getValue(1, 1) == b.getValue(2, 0)) {
			p = b.getValue(0, 2);
		} else {
			p = 0;
		}
		
		return p;
	}
	
	
}
