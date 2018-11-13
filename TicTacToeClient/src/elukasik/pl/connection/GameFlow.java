package elukasik.pl.connection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import elukasik.pl.toolkit.RequestToolkit;
import elukasik.pl.view.TicTacToeBoard;
import pl.elukasik.model.Board;
import pl.elukasik.model.Message;
import pl.elukasik.model.Request;

/**
 * 
 * @author plukasik
 *
 */
public class GameFlow implements Runnable {

	private String userName;

	private String host = "127.0.0.1";
	private int port = 8888;
	private boolean gameEnd = false;
	private String endError = null;
	private int playerId = -1;
	
	private Object lock = new Object();

	private TicTacToeBoard gameBoard;
	
	private Message toSendMsg;

	public GameFlow(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public void run() {

		try (Socket socket = new Socket(host, port)) {

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

			Message gto = RequestToolkit.getStartGameRequest(userName);

			oos.writeObject(gto);
			oos.flush();
			oos.reset();

			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			while (!isGameEnd()) {
				
				Object obj = ois.readObject();


				if (obj instanceof Message) {

					gto = (Message) obj;

					switch (gto.getRequest()) {

					case WAITING_P2:
						updateStatusLbl("Waiting for player 2");
						break;
						
					case START_GAME:
						updateStatusLbl("Game started");
						playerId = gto.getPlayerId();
						Board board = gto.getBoard();
						gameBoard.drawBoard(board);
						break;
						
					case MOVE:
						board = gto.getBoard();
						gameBoard.drawBoard(board);
						if (gto.getPlayerId() == playerId) {
							updateStatusLbl("Your move");
							synchronized (lock) {
								lock.wait();
							}
							Message msg = getToSendMsg();
							oos.writeObject(msg);
							oos.flush();
						} else {
							updateStatusLbl("Enemy move");
						}
						
						break;
						
						
					case GAME_END:
						board = gto.getBoard();
						gameBoard.drawBoard(board);
						if (gto.getPlayerId() == playerId) {
							JOptionPane.showMessageDialog(gameBoard, "You win");
						} else {
							JOptionPane.showMessageDialog(gameBoard, "You lose");
						}
						setErrMsg(null);
						setGameEnd(true);
						break;
						
					case ENEMY_NOT_RESPOND:
						if (gto.getPlayerId() == playerId) {
							JOptionPane.showMessageDialog(gameBoard, "Enemy Not Respond.\nYou win");
						} else {
							JOptionPane.showMessageDialog(gameBoard, "You lose");
						}
						setErrMsg(null);
						setGameEnd(true);
						break;
					}	

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			setErrMsg(e.getMessage());
			setGameEnd(true);
		}

	}

	public void notifyOnClick(int x, int y) {
		System.out.println("Clicked " + x + " x " + y);
		Message tmp = new Message(Request.MOVE);
		tmp.setX(x);
		tmp.setY(y);
		
		setToSendMsg(tmp);
		
		synchronized(lock) {
			lock.notify();
		}
	}

	public synchronized boolean isGameEnd() {
		return gameEnd;
	}

	public synchronized void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
	}

	public void setGameBoard(TicTacToeBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public synchronized void setToSendMsg(Message msg) {
		this.toSendMsg = msg;
	}
	
	public synchronized String getErrMsg() {
		return endError;
	}
	
	public synchronized void setErrMsg(String endErr) {
		this.endError = endErr;
	}
	
	public synchronized Message getToSendMsg() {
		return this.toSendMsg;
	}

	public void updateStatusLbl(String text) {
		this.gameBoard.setStatusText(text);
	}
}
