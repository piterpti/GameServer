package pl.elukasik.model;

import java.io.Serializable;

public class GameTransportObj implements Serializable {

	private String userName;
	private Request request;
	private boolean yourMove = false;
	
	private int x;
	private int y;
	
	/** Wait for player 2 join */
	private boolean waitForP2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GameTransportObj(String userName, Request request) {
		this.userName = userName;
		this.request = request;
	}
	
	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "GameTransportObj [userName=" + userName + "]";
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

	public boolean isWaitForP2() {
		return waitForP2;
	}

	public void setWaitForP2(boolean waitForP2) {
		this.waitForP2 = waitForP2;
	}	
}
