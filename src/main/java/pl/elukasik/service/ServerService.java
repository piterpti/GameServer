package pl.elukasik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import pl.elukasik.server.GameServer;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:gameServer.properties")
public class ServerService implements InitializingBean {
	
	private static boolean shutdown = false;

	private Logger logger = LoggerFactory.getLogger(ServerService.class);
	private GameServer gameServer;
	
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
		
		Thread server = new Thread(gameServer, gameServer.getClass().getName());
		server.setDaemon(true);
		server.start();
	}
}
