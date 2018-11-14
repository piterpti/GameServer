package pl.elukasik.server;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import pl.elukasik.model.GameHandler;

/**
 * Thread that removes games older than 1 hour
 * 
 * @author plukasik
 *
 */
public class RemoveOldGamesThread implements Runnable {

	/**
	 * Time after which this thread remove game from memory
	 */
	private static final long MAX_GAME_TIME = TimeUnit.MINUTES.toMillis(60);
	
	private GameServer gs;
	
	private Queue<GameHandler> gamesToDelete = new ArrayBlockingQueue<>(100);
	
	public RemoveOldGamesThread() {
		
	}
	
	@Override
	public void run() {
		
		
		
	}

}
