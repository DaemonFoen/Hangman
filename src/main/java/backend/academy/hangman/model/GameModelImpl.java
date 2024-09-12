package backend.academy.hangman.model;

import backend.academy.hangman.controller.Session;
import backend.academy.hangman.model.api.GameModel;
import backend.academy.hangman.model.data.GameLevel;
import backend.academy.hangman.model.data.api.WordsRepository;
import backend.academy.hangman.util.SourceLoader;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameModelImpl implements GameModel {

    private final WordsRepository wordsRepository;
    private State state;
    private final int attempts;

    public GameModelImpl(String sourceFilePath, int attempts) {
        this.wordsRepository = SourceLoader.load(sourceFilePath);
        this.attempts = attempts;
    }

    @Override
    public List<String> getCategories() {
        return wordsRepository.getCategories();
    }

    @Override
    public Session start(GameLevel level, String category) {
        state = new State(
            category == null ? wordsRepository.getRandomCategoryWord(level) : wordsRepository.getWord(level, category),
            attempts);
        log.debug("Word: {}", state.word());
        return mapStateToSession(state);
    }

    @Override
    public Session process(Character input) {
        if (state == null) {
            log.error("Game has not been started, process should be called only after start ");
            throw new IllegalStateException("Game has not been started");
        }
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
