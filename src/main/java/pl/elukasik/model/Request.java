package pl.elukasik.model;

/**
 * Requests sending from client->server, server->client
 * @author piter
 *
 */
public enum Request {
	
	/**
	 * Request that means star game
	 */
	START_GAME,
	
	/**
	 * Request that means player move on the game board 
	 */
	MOVE,
	
	WAITING_P2,
	
	GAME_END
}
