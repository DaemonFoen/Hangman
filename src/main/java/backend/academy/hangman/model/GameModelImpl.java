package backend.academy.hangman.model;

import backend.academy.hangman.controller.Session;
import backend.academy.hangman.model.api.GameModel;
import backend.academy.hangman.model.data.GAME_LEVEL;
import backend.academy.hangman.model.data.api.WordsRepository;
import backend.academy.hangman.util.SourceLoader;
import java.util.List;

public class GameModelImpl implements GameModel {

    private final WordsRepository wordsRepository;
    private State state;

    public GameModelImpl() {
        this.wordsRepository = SourceLoader.load();
    }

    @Override
    public List<String> getCategories() {
        return wordsRepository.getCategories();
    }

    @Override
    public Session start(GAME_LEVEL level, String category) {
        state = new State(
            category == null ? wordsRepository.getRandomCategoryWord(level) : wordsRepository.getWord(level, category));
        return mapStateToSession(state);
    }

    @Override
    public Session process(Character input) {
        return mapStateToSession(state.process(input));
    }

    private Session mapStateToSession(State state) {
        return new Session(state.word(), state.attempts(), state.usedChars());
    }
}
