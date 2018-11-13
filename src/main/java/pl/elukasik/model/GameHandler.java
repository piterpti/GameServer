package pl.elukasik.model;

import static pl.elukasik.tollkit.GameToolkit.checkIsEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.connector.PlayerConnector;
import pl.elukasik.service.GameDAOService;
import pl.elukasik.tollkit.GameToolkit;

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

	private GameDAOService gameDAO;

	private boolean waitingPlayer2 = true;

	private long startTime;

	/** Time for each player move */
	private long TIME_INTERVAL = TimeUnit.SECONDS.toMillis(30);

	public GameHandler(final long gameId, Socket socketP1, Message obj, ObjectInputStream ois, GameDAOService dao) {
		this.socketP1 = socketP1;
		this.gameId = gameId;
		this.oisP1 = ois;
		this.gameDAO = dao;
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

	public synchronized boolean isWaitingPlayer2() {
		return waitingPlayer2;
	}

	private synchronized void setWaitingPlayer2(boolean waitingPlayer2) {
		this.waitingPlayer2 = waitingPlayer2;
	}

	@Override
	public void run() {

		startTime = System.currentTimeMillis();
		Message msg = new Message(Request.WAITING_P2);

		Board board = new Board();

		try {
			connP1 = new PlayerConnector(socketP1, oisP1);
			connP1.sendMessage(msg);

			if (waitForPlayer2()) {

				connP2 = new PlayerConnector(socketP2, oisP2);

				int endResult = 0;
				int currPlayer = 0;
				boolean changePlayer = true;
				boolean notRespond = false;

				msg = new Message(Request.START_GAME, 1, board);
				sendMsg(msg, new Message(Request.START_GAME, 2, board));

				while ((endResult = checkIsEnd(board)) == 0) {

					if (changePlayer) {
						currPlayer = getCurrentPlayer(currPlayer);
						gameDAO.saveGameState(GameToolkit.convertBoard2GameState(board, gameId, false));
					}

					msg = new Message(Request.MOVE, currPlayer, board);
					sendMsg(msg);

					msg = getPlayerResponse(currPlayer);

					if (msg == null || msg.getRequest() == Request.ENEMY_NOT_RESPOND) {
						
						endResult = getCurrentPlayer(currPlayer);
						msg = new Message(Request.ENEMY_NOT_RESPOND, endResult, null);
						notRespond = true;
						sendMsg(msg);
						break;
						
					} else {
						
						changePlayer = board.fieldClicked(msg.getX(), msg.getY(), currPlayer);
						
					}
				}

				gameDAO.saveGameState(GameToolkit.convertBoard2GameState(board, gameId, true));

				if (!notRespond) {
					msg = new Message(Request.GAME_END, endResult, board);
					sendMsg(msg);
				}

				closeResources();

			}

		} catch (IOException e) {

			logger.error("GameHandler error", e);

		}
	}

	private boolean sendMsg(Message... msg) {

		if (msg.length == 1) {
			return connP1.sendMessage(msg[0]) && connP2.sendMessage(msg[0]);
		} else {
			return connP1.sendMessage(msg[0]) && connP2.sendMessage(msg[1]);
		}

	}

	private PlayerConnector getPlayerConnector(final int playerId) {
		return playerId == 1 ? connP1 : connP2;
	}

	private Message getPlayerResponse(final int playerId) {

		Message msgFromPlayer = null;

		long waitTo = System.currentTimeMillis() + TIME_INTERVAL;
		while (msgFromPlayer == null) {

			if (System.currentTimeMillis() > waitTo) {
				return new Message(Request.ENEMY_NOT_RESPOND);
			}

			msgFromPlayer = getPlayerConnector(playerId).getMessage();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				logger.error("Interrupted", e);
				return null;
			}

		}

		return msgFromPlayer;
	}

	private int getCurrentPlayer(int currentPlayer) {
		return currentPlayer == 1 ? 2 : 1;
	}

	private boolean waitForPlayer2() {

		while (isWaitingPlayer2()) {

			// TODO dodać czas na podłączenie 2 gracza

			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
				return false;
			}
		}

		return true;
	}

	private void closeResources() {

		try {
			connP1.close();
		} catch (IOException e) {
			logger.error("", e);
		}

		try {
			connP2.close();
		} catch (IOException e) {
			logger.error("", e);
		}

	}
}
