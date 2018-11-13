package pl.elukasik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.elukasik.dao.model.GameState;
import pl.elukasik.dao.model.GameStateRepository;

@Service
public class GameDAOService {

	@Autowired
	private GameStateRepository repository;
	
	public void setRepository(GameStateRepository repository) {
		this.repository = repository;
	}
	
	public GameState saveGameState(GameState state) {
		repository.save(state);
		repository.flush();
		return state;
	}
	
	
}
