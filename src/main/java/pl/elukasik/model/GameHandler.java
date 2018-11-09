package pl.elukasik.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.connector.PlayerConnector;

/**
 * Game model
 * 
 * @author piter
 *
 */
public class GameHandler implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(GameHandler.class);
	
	/**
	 * Unique game id
	 */
	private long gameId;
	
	private Socket socketP1;
	private Socket socketP2;
	
	private PlayerConnector connP1;
	private PlayerConnector connP2;
	
	private ObjectInputStream oisP1;
	private ObjectInputStream oisP2;
	
	private boolean waitingPlayer2 = true;
	
	public GameHandler(final long gameId, Socket socketP1, Message obj, ObjectInputStream ois) {
		this.socketP1 = socketP1;
		this.gameId = gameId;
		this.oisP1 = ois;
	}
	
	
	
	public void setSocketP2(Socket socketP2, Message obj, ObjectInputStream ois) {
		this.socketP2 = socketP2;
		oisP2 = ois;
		setWaitingPlayer2(false);
	}
	
	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	/**
	 * Game Logic handled here 
	 */
	@Override
	public void run() {
		
		try {
			connP1 =  new PlayerConnector(socketP1, oisP1);
			
			Message msg = new Message(Request.WAITING_P2);
			connP1.sendMessage(msg);
			
			while (isWaitingPlayer2()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					return;
				}
			}
			
			connP2 = new PlayerConnector(socketP2, oisP2);
			
			Board board = new Board();
			msg = new Message(Request.START_GAME);
			msg.setBoard(board);
			msg.setPlayerId(1);
			connP1.sendMessage(msg);
			msg.setPlayerId(2);
			connP2.sendMessage(msg);
			
			
			int endResult = 0;
			while ((endResult = Board.checkIsEnd(board)) == 0) {
				
				msg = new Message(Request.MOVE);
				msg.setPlayerId(1);
				msg.setBoard(board);
				connP1.sendMessage(msg);
				connP2.sendMessage(msg);
				
				
				Message msgMove = null;
				while (msgMove == null) {
					msgMove = connP1.getMessage();
					Thread.sleep(10);
				}
				board.fieldClicked(msgMove.getX(), msgMove.getY(), 1);
				
				endResult = Board.checkIsEnd(board);
				if (endResult != 0) {
					break;
				}
				
				msg = new Message(Request.MOVE);
				msg.setPlayerId(2);
				msg.setBoard(board);
				connP1.sendMessage(msg);
				connP2.sendMessage(msg);
				
				
				msgMove = connP2.getMessage();
				while (msgMove == null) {
					msgMove = connP2.getMessage();
					Thread.sleep(10);
				}
				board.fieldClicked(msgMove.getX(), msgMove.getY(), 2);
			}
			
			msg = new Message(Request.GAME_END);
			msg.setPlayerId(endResult);
			msg.setBoard(board);
			connP1.sendMessage(msg);
			connP2.sendMessage(msg);	
			
			connP1.close();
			connP2.close();
			
		} catch (IOException | InterruptedException e) {
			
		}		
	}
	
	public synchronized boolean isWaitingPlayer2() {
		return waitingPlayer2;
	}
	
	private synchronized void setWaitingPlayer2(boolean waitingPlayer2) {
		this.waitingPlayer2 = waitingPlayer2;
	}
	
	
	
}
