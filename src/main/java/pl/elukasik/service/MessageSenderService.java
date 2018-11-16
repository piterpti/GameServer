package pl.elukasik.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger logger = LoggerFactory.getLogger(MessageSenderService.class);

	public MessageSenderService() {
		executor = Executors.newFixedThreadPool(20);
	}

	/**
	 * Send msg to passed player
	 * 
	 * @param pConn
	 *            player connector
	 * @param msg
	 *            message to send
	 */
	public void sendMessage(PlayerConnector pConn, Message msg, final boolean closeConn) {

		executor.execute(() -> {

			try {
				if (msg != null) {
					pConn.sendMessage(msg);
				}

			} catch (Exception e) {
				logger.error("Error when sending msg", e);
			} finally {
				if (closeConn) {
					try {
						pConn.close();
					} catch (Exception e) {
						// ignore it
					}
				}
			}

		});
	}

}
