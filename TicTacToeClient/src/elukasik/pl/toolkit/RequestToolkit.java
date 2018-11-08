package elukasik.pl.toolkit;

import pl.elukasik.model.GameTransportObj;
import pl.elukasik.model.Request;

public class RequestToolkit {
	
	public static GameTransportObj getStartGameRequest(final String userName) {
		GameTransportObj obj = new GameTransportObj(userName, Request.START_GAME);
		return obj;
	}

}
