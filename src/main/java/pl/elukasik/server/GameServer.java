package pl.elukasik.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.model.Game;
import pl.elukasik.service.ServerService;
import pl.elukasik.tollkit.SocketToolkit;

/**
 * Application server logic
 * Here are handled all incoming/outgoing connections
 * 
 * @author piter
 *
 */
public class GameServer implements Runnable {
	
	private Logger logger = LoggerFactory.getLogger(GameServer.class);
	
	private List<Game> games = new LinkedList<>();
	
	private ServerSocket serverSocket;
	
	private final int port;
	
	public GameServer(final int port) {
		this.port = port;
		logger.info("Creating GameServer");
	}

	@Override
	public void run() {
		
		try {
			serverSocket = new ServerSocket(port);
			logger.info("Server started on port " + port);
			
			while (!ServerService.isShutdown()) {
				
				final Socket socket = serverSocket.accept();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						new SocketToolkit(socket);
						
					}
				}).start();
			}
			
		} catch (IOException e) {
			logger.error("Cannot start server on port " + port, e);
			ServerService.setShutdown(true);
		}
	}

}
