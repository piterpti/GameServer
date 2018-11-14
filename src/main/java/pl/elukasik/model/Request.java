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
	
	/**
	 * Waiting for player 2 join
	 */
	WAITING_P2,
	
	/**
	 * End game request
	 */
	GAME_END,
	
	/**
	 * Enemy did not respond in properly time
	 */
	ENEMY_NOT_RESPOND,
	
	/**
	 * Player 2 did not join
	 */
	PLAYER_2_TIMEOUT
}
