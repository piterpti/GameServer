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
import org.springframework.beans.factory.annotation.Autowired;

import pl.elukasik.dao.model.GameState;
import pl.elukasik.model.GameHandler;
import pl.elukasik.model.Message;
import pl.elukasik.service.GameDAOService;
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
	
	private List<GameHandler> games = new LinkedList<>();
	
	private final int port;
	
	private GameDAOService gameDAO;
	
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
				
				if (obj instanceof Message) {
				
					Optional<GameHandler> gameObj = games.stream().filter(g -> g.isWaitingPlayer2()).findFirst();
					
					if (gameObj.isPresent()) {
						// joining to existing game
						GameHandler game = gameObj.get();
						game.setSocketP2(socket, (Message)obj, ois);
						
					} else {
						// creating new Game
						
						GameState gs = new GameState();
						gs.setGameState("");
						gameDAO.saveGameState(gs);
						
						GameHandler game = new GameHandler(gs.getId(), socket, (Message)obj, ois, gameDAO);
						games.add(game);
						
						Thread gameThread = new Thread(game);
						gameThread.setDaemon(true);
						gameThread.start();
						
					}
				}
				
				
			}
			
		} catch (Exception e) {
			logger.error("Cannot start server on port " + port, e);
			ServerService.setShutdown(true);
		}
	}
	
	public void setGameDAO(GameDAOService gameDAO) {
		this.gameDAO = gameDAO;
	}
}
