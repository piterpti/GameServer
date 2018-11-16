package pl.elukasik.server;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.elukasik.model.GameHandler;
import pl.elukasik.service.ServerService;

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
	private static final long MAX_GAME_TIME = TimeUnit.MINUTES.toMillis(10);

	private Logger logger = LoggerFactory.getLogger(RemoveOldGamesThread.class);

	private GameServer gs;

	public RemoveOldGamesThread(GameServer gs) {
		this.gs = gs;
	}

	@Override
	public void run() {

		GameHandler gh;
		while (!ServerService.isShutdown()) {

			List<GameHandler> games = gs.getGames();
			synchronized (games) {

				for (Iterator<GameHandler> iter = games.listIterator(); iter.hasNext();) {

					gh = iter.next();
					if (gh.isEnd() || gh.getStartTime() + MAX_GAME_TIME > System.currentTimeMillis()) {
						logger.info("Removing game: " + gh.getGameId());
						iter.remove();
					}
				}

			}

			try {
				Thread.sleep(TimeUnit.MINUTES.toMillis(1));
			} catch (InterruptedException e) {
				logger.error("", e);
				break;
			}
		}

	}

}
