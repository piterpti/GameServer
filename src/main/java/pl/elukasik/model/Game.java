package pl.elukasik.model;

import java.net.Socket;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.tollkit.SocketToolkit;

/**
 * Game model
 * 
 * @author piter
 *
 */
public class Game {
	
	private static Logger logger = LoggerFactory.getLogger(Game.class);
	
	/**
	 * Unique game id
	 */
	private long gameId;
	
	private volatile boolean waitingForPlayer2 = true;
	
	private Socket socketP1;
	private Socket socketP2;
	
	private String userName1;
	private String userName2;
	
	public Game(final long gameId, Socket socketP1, GameTransportObj obj) {
		this.socketP1 = socketP1;
		this.gameId = gameId;
		userName1 = obj.getUserName();
	}
	
	
	
	public void setSocketP2(Socket socketP2, GameTransportObj obj) {
		this.socketP2 = socketP2;
		logger.info("Player 2 join to game: " + gameId);
		waitingForPlayer2 = false;
		userName2 = obj.getUserName();
	}
	
	public void startGame() {
		
		logger.info("Game " + gameId + " started, player1: " + userName1 + ", player2: " + userName2);
		
		GameTransportObj obj1 = new GameTransportObj(userName1, Request.START_GAME);
		obj1.setYourMove(true);
		GameTransportObj obj2 = new GameTransportObj(userName2, Request.START_GAME);
		obj2.setYourMove(false);
		
		SocketToolkit.sendResponse(socketP1, obj1);
		SocketToolkit.sendResponse(socketP2, obj2);
		
	}
	
	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	
	public boolean isWaitingForPlayer2() {
		return waitingForPlayer2;
	}
	
}
