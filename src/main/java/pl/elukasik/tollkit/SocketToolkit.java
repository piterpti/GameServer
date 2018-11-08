package pl.elukasik.tollkit;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.model.GameTransportObj;

/**
 * Toolkit for reading/writing data to sockets
 * 
 * @author piter
 *
 */
public class SocketToolkit {

	private static Logger logger = LoggerFactory.getLogger(SocketToolkit.class);
	
	public static Optional<GameTransportObj> getGameTOFromSocket(Socket socket) {
		
		InputStream is = null;
		ObjectInputStream ois = null;
		GameTransportObj result = null;
		
		try {
		try {
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);
		} catch (IOException e) {
			logger.error("Can't get input stream", e);
		}
		
		if (ois != null) {
			
			try {
				Object obj = ois.readObject();
				if (obj instanceof GameTransportObj) {
					result = (GameTransportObj) obj;
				}
				
			} catch (ClassNotFoundException | IOException e) {
				logger.error("", e);
			}
		}
		
		} finally {
			closeResources(ois, is);
		}
		
		return Optional.ofNullable(result);
		
	}
	
	public static boolean sendResponse(Socket s, Object obj) {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(obj);
			oos.flush();
			
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	private static void closeResources(Closeable ...resource) {
		Stream<Closeable> stream = Arrays.stream(resource);
		stream.forEach(r -> {
			if (r != null) {
				try {
					r.close();
				} catch (Exception e) {
					logger.error("Close resource error", e);
				}
			}
		});
	}
}
