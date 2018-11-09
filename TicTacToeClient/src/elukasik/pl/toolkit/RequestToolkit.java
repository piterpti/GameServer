package elukasik.pl.toolkit;

import pl.elukasik.model.Message;
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
	public static Message getStartGameRequest(final String userName) {
		Message obj = new Message(userName, Request.START_GAME);
		return obj;
	}
	
	/**
	 * @param userName
	 * @param x
	 * @param y
	 * @return request that means user move on the game board
	 */
	public static Message getMoveRequest(final String userName, int x, int y) {
		Message obj = new Message(userName, Request.MOVE);
		
		obj.setX(x);
		obj.setY(y);
		
		return obj;
	}

}
