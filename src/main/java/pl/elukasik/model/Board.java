package pl.elukasik.model;

import java.io.Serializable;

public class Board implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private static final int EMPTY_FIELD = 0;
	
	
	private int[][] gameBoard;
	
	public Board() {
		gameBoard = new int[3][3];
		setAllBoardValues(EMPTY_FIELD);
	}
	
	
	public void setAllBoardValues(int player) {
		for (int x = 0; x < 3; x++){
			for (int y = 0; y < 3; y++) {
				gameBoard[x][y] = player;
			}
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int x = 0; x < 3; x++){
			for (int y = 0; y < 3; y++) {
				sb.append(gameBoard[x][y]).append(" | ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * @param x coordinate to click
	 * @param y coordinate to click
	 * @param playerId player unique id
	 * @return was field clicked
	 */
	public boolean fieldClicked(int x, int y, int playerId) {
		
		if (gameBoard[x][y] != EMPTY_FIELD) {
			return false;
		}
		
		gameBoard[x][y] = playerId;
		
		return true;
	}
	
	public int getValue(int x, int y) {
		return gameBoard[x][y];
	}
	
	public int[][] getBoard() {
		return gameBoard;
	}
}
