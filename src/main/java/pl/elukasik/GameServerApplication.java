package pl.elukasik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import pl.elukasik.service.GameDAOService;
import pl.elukasik.service.ServerService;

/**
 * Main application class
 * 
 * @author piter
 *
 */
@SpringBootApplication
public class GameServerApplication {

	private static Logger logger = LoggerFactory.getLogger(GameServerApplication.class);
	
	/**
	 * Application start
	 * @param args not handling
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		
		ConfigurableApplicationContext ctx = SpringApplication.run(GameServerApplication.class, args);
		
		while (!ServerService.isShutdown()) {
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				logger.info("Main thread interruped!?", e);
				ctx.close();
				throw e;
				
			}
		}
		
		ctx.close();
	}
}
