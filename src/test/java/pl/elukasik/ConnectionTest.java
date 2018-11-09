package pl.elukasik;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Test;

import pl.elukasik.model.Message;
import pl.elukasik.model.Request;

public class ConnectionTest {
	
	private final String URL = "localhost";
	private final int PORT = 8888;
	
	@Test
	public void testSendObject() throws Exception {
		
		Socket socket = new Socket(URL, PORT);
		
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		Message gto = new Message("John", Request.START_GAME);
		
		oos.writeObject(gto);
		oos.flush();
		
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		Object obj = ois.readObject();
		
		System.out.println(obj.toString());
	}

}
