package pl.elukasik.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.elukasik.dao.model.GameState;
import pl.elukasik.model.Board;
import pl.elukasik.tollkit.GameToolkit;


public class GameToolkitTest {
	
	@Test
	public void testConvertBoard2GameState() {
		
		Board b = new Board();
		b.fieldClicked(0, 0, 1);
		b.fieldClicked(1, 0, 1);
		b.fieldClicked(2, 1, 1);
		b.fieldClicked(0, 2, 1);
		
		System.out.println(b);
		
		GameState gs = GameToolkit.convertBoard2GameState(b, 1, false);
		
		System.out.println(gs.toString());
		
		Board b2 = GameToolkit.convertGameState2Board(gs);
		
		System.out.println(b2);
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				assertEquals(b.getBoard()[x][y], b2.getBoard()[x][y]);
			}
		}
	}

}
