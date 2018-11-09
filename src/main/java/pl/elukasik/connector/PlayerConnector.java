package pl.elukasik.connector;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.model.Message;

public class PlayerConnector implements Closeable {
	
	private Logger logger = LoggerFactory.getLogger(PlayerConnector.class);

	private ObjectOutputStream oos;
	private InputDataHandler dataHandler;
	
	private boolean end = false;

	public PlayerConnector(Socket socket, ObjectInputStream ois) throws IOException {
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		dataHandler = new InputDataHandler(ois);
		Thread t = new Thread(dataHandler);
		t.setDaemon(true);
		t.start();
		
	}
	
	public boolean sendMessage(Message msg) {
		
		try {
			oos.writeObject(msg);
			oos.flush();
			oos.reset();
			
			return true;
			
		} catch (IOException e) {
			logger.error("Error sending msg", e);
		}
		
		return false;
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

			while (!isEnd() && !Thread.interrupted()) {
				
				try {
					Object obj = ois.readObject();
					
					if (obj instanceof Message) {
						logger.info(obj.toString());
						setMessage((Message) obj);
					}
					
				} catch (ClassNotFoundException | IOException e) {
					logger.error("Can't read object", e);
					return;
				}
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

	@Override
	public void close() throws IOException {
		oos.close();
	}

}
