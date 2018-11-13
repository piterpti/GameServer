package pl.elukasik.dao.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GameState entity represents game state in DB
 * 
 * @author plukasik
 *
 */
@Entity
@Table(name = "game_state")
public class GameState {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	
	@Column(name = "game_state", length = 9)
	private String gameState;
	
	@Column(name = "is_end")
	private boolean end = false;
	
	public GameState() {
		// JPA required empty construcor
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "GameState [id=" + id + ", gameState=" + gameState + ", end=" + end + "]";
	}
}
