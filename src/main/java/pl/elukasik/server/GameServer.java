package pl.elukasik.server;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.model.Game;
import pl.elukasik.model.GameTransportObj;
import pl.elukasik.service.ServerService;

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
	
	
	private final int port;
	
	private AtomicLong gameId = new AtomicLong();
	
	public GameServer(final int port) {
		this.port = port;
		logger.info("Creating GameServer");
	}

	@Override
	public void run() {
		
		
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			
			logger.info("Server started on port " + port);
			
			while (!ServerService.isShutdown()) {
				
				final Socket socket = serverSocket.accept();
				
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Object obj = ois.readObject();
				
				if (obj instanceof GameTransportObj) {
				
					Optional<Game> gameObj = games.stream().filter(g -> g.isWaitingForPlayer2()).findFirst();
					
					if (gameObj.isPresent()) {
						// joining to existing game
						Game game = gameObj.get();
						game.setSocketP2(socket, (GameTransportObj)obj);
						game.startGame();
						
					} else {
						// creating new Game
						Game game = new Game(gameId.getAndIncrement(), socket, (GameTransportObj)obj);
						games.add(game);
						
					}
				}
				
				
			}
			
		} catch (Exception e) {
			logger.error("Cannot start server on port " + port, e);
			ServerService.setShutdown(true);
		}
	}
}
