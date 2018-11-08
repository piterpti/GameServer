package pl.elukasik.model;

import java.net.Socket;

/**
 * Game model
 * 
 * @author piter
 *
 */
public class Game {
	
	/**
	 * Unique game id
	 */
	private long gameId;
	
	private Socket socketP1;
	private Socket socketP2;
	
	public Game() {
		
	}

}
