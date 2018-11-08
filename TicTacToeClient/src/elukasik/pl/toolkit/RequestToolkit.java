package elukasik.pl.toolkit;

import pl.elukasik.model.GameTransportObj;
import pl.elukasik.model.Request;

/**
 * Request generator toolkit
 * 
 * @author piter
 *
 */
public class RequestToolkit {
	
	/**
	 * @param userName
	 * @return request that starts game
	 */
	public static GameTransportObj getStartGameRequest(final String userName) {
		GameTransportObj obj = new GameTransportObj(userName, Request.START_GAME);
		return obj;
	}
	
	/**
	 * @param userName
	 * @param x
	 * @param y
	 * @return request that means user move on the game board
	 */
	public static GameTransportObj getMoveRequest(final String userName, int x, int y) {
		GameTransportObj obj = new GameTransportObj(userName, Request.MOVE);
		
		obj.setX(x);
		obj.setY(y);
		
		return obj;
	}

}
