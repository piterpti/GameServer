package pl.elukasik.model;

import java.io.Serializable;

public class GameTransportObj implements Serializable {

	private String userName;
	private Request request;
	private boolean yourMove = false;
	
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
}
