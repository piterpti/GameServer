package pl.elukasik.tollkit;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Toolkit for reading/writing data to sockets
 * 
 * @author piter
 *
 */
public class SocketToolkit {

	private Logger logger = LoggerFactory.getLogger(SocketToolkit.class);
	
	public SocketToolkit(Socket socket) {
		
		InputStream is;
		ObjectInputStream ois;
		try {
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);
			
			Object obj = ois.readObject();
			if (obj != null) {
				logger.info("Received:" + obj.toString()+ ", class: " + obj.getClass().getName());
			}
			
			
		} catch (Exception e) {
			logger.error("Error reading socket", e);
		}
	}
}
