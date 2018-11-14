package pl.elukasik.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import pl.elukasik.connector.PlayerConnector;
import pl.elukasik.model.Message;

/**
 * Service to send messages from client
 * 
 * @author plukasik
 *
 */
@Service

public class MessageSenderService {

	private Executor executor;
	
	public MessageSenderService() {
		executor = Executors.newFixedThreadPool(20);
	}

	/**
	 * Send msg to passed player
	 * @param pConn player connector
	 * @param msg message to send
	 */
	public void sendMessage(PlayerConnector pConn, Message msg) {
		
		executor.execute(() -> {
			
			try {
				pConn.sendMessage(msg);
			} catch (Exception e) {
				// that exception is logged in sendMsg method
			}
		});
	}
	
		
}
