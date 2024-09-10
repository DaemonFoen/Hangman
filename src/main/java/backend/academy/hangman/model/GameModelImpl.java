package backend.academy.hangman.model;

import backend.academy.hangman.controller.Session;
import backend.academy.hangman.model.api.GameModel;
import backend.academy.hangman.model.data.GAME_LEVEL;
import backend.academy.hangman.model.data.api.WordsRepository;
import backend.academy.hangman.util.SourceLoader;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameModelImpl implements GameModel {

    private final WordsRepository wordsRepository;
    private State state;

    public GameModelImpl(String sourceFilePath) {
        this.wordsRepository = SourceLoader.load(sourceFilePath);
    }

    @Override
    public List<String> getCategories() {
        return wordsRepository.getCategories();
    }

    @Override
    public Session start(GAME_LEVEL level, String category) {
        state = new State(
            category == null ? wordsRepository.getRandomCategoryWord(level) : wordsRepository.getWord(level, category));
        log.debug("Word: {}", state.word());
        return mapStateToSession(state);
    }

    @Override
    public Session process(Character input) {
        return mapStateToSession(state.process(input));
    }

    @Override
    public boolean gameIsWon() {
        return state.gameIsWon();
    }

    private Session mapStateToSession(State state) {
        return new Session(state.word(), state.hint(), state.attempts(), state.usedChars());
    }
}
