package elukasik.pl.connection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import elukasik.pl.toolkit.RequestToolkit;
import elukasik.pl.view.TicTacToeBoard;
import pl.elukasik.model.GameTransportObj;

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
	
	private TicTacToeBoard gameBoard;

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
			
			GameTransportObj gto = RequestToolkit.getStartGameRequest(userName);
			
			oos.writeObject(gto);
			oos.flush();
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Object obj = ois.readObject();
			
			if (obj instanceof GameTransportObj) {
				
				gto = (GameTransportObj) obj;
				
				System.out.println(gto.toString());
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			setGameEnd(true);
		}
		
		
	}
	
	public void notifyOnClick(int x, int y) {
		System.out.println("Clicked " + x + " x " + y);
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
}
