package pl.elukasik.model;

import java.io.Serializable;

public class Message implements Serializable {

	private String userName;
	private Request request;
	private boolean yourMove = false;
	private int playerId;
	
	private int x;
	private int y;
	
	private Board board;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Message(Request request) {
		this.request = request;
	}

	public Message(String userName, Request request) {
		this.userName = userName;
		this.request = request;
	}
	
	public Message(Request request, int playerId, Board board) {
		this.request = request;
		this.playerId = playerId;
		this.board = board;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "Message [userName=" + userName + ", request=" + request + "]";
	}

	public void setYourMove(boolean yourMove) {
		this.yourMove = yourMove;
	}
	
	public boolean isYourMove() {
		return yourMove;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
