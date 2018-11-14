package pl.elukasik.connector;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.exception.ConnectionLostException;
import pl.elukasik.model.Message;

public class PlayerConnector implements Closeable {
	
	private Logger logger = LoggerFactory.getLogger(PlayerConnector.class);

	private ObjectOutputStream oos;
	private InputDataHandler dataHandler;
	private boolean connected = false;
	
	private boolean end = false;
	
	private Queue<Message> msgs = new ArrayDeque<>();

	public PlayerConnector(Socket socket, ObjectInputStream ois) throws IOException {
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		dataHandler = new InputDataHandler(ois);
		Thread t = new Thread(dataHandler);
		t.setDaemon(true);
		t.start();
		
		setConnected(true);
	}
	
	public boolean sendMessage(Message msg) throws ConnectionLostException {
		
		try {
			oos.writeObject(msg);
			oos.flush();
			oos.reset();
			
			return true;
			
		} catch (IOException e) {
			logger.error("Error sending msg", e);
			setConnected(false);
			throw new ConnectionLostException("Can't send message", e);
		}
	}
	
	public Message getMessage() {
		return dataHandler.getMessage();
	}

	class InputDataHandler implements Runnable {

		private Message msg;
		ObjectInputStream ois;

		public InputDataHandler(ObjectInputStream ois) {
			this.ois = ois;
		}

		@Override
		public void run() {

			try {
				while (!isEnd() && !Thread.interrupted()) {

					Object obj = ois.readObject();

					if (obj instanceof Message) {
						setMessage((Message) obj);
					}

				}
			} catch (SocketException se) {

				if (!se.getMessage().contains("Socket closed")) {
					logger.error("Can't read object", se);
				}

			} catch (ClassNotFoundException | IOException e) {
				logger.error("Can't read object", e);
				
			} finally {
				
				setConnected(false);
			}

		}
		
		public synchronized void setMessage(Message msg) {
			this.msg = msg;
		}
		
		public synchronized Message getMessage() {
			Message tmpMsg = this.msg;
			this.msg = null;
			return tmpMsg;
		}

	}
	
	public synchronized boolean isEnd() {
		return end;
	}
	
	public synchronized void setEnd(boolean end) {
		this.end = end;
	}
	
	public synchronized void setConnected(boolean connected) {
		logger.info("Player setConnected - " + Boolean.toString(connected));
		this.connected = connected;
	}
	
	/**
	 * @return is connection to client active
	 */
	public synchronized boolean isConnected() {
		return connected;
	}

	@Override
	public void close() throws IOException {
		oos.close();
	}

}
