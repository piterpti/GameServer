package pl.elukasik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import pl.elukasik.server.GameServer;
import pl.elukasik.server.RemoveOldGamesThread;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:gameServer.properties")
public class ServerService implements InitializingBean {
	
	private static boolean shutdown = false;

	private Logger logger = LoggerFactory.getLogger(ServerService.class);
	private GameServer gameServer;
	
	@Autowired
	private GameDAOService gameDAO;
	
	@Autowired
	private MessageSenderService mss;
	
	/**
	 * Server listening port
	 */
	@Value("${port}")
	private int port;
	
	public ServerService() {
		logger.info("Started ServerService");
	}
	
	public static synchronized boolean isShutdown() {
		return shutdown;
	}
	
	public static synchronized void setShutdown(boolean aShutdown) {
		shutdown = aShutdown;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		gameServer = new GameServer(port);
		gameServer.setGameDAO(gameDAO);
		gameServer.setMss(mss);
		
		Thread server = new Thread(gameServer, gameServer.getClass().getName());
		server.setDaemon(true);
		server.start();
		
		RemoveOldGamesThread orgThread = new RemoveOldGamesThread(gameServer);
		Thread removesOldGamesT = new Thread(orgThread);
		removesOldGamesT.setDaemon(true);
		removesOldGamesT.setName("RemoveOldGamesThread");
		removesOldGamesT.start();
	}
}
