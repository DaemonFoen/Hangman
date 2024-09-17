package backend.academy.hangman.model.impl;

import backend.academy.hangman.controller.impl.GameState;
import backend.academy.hangman.controller.impl.SessionDTO;
import backend.academy.hangman.exception.InvalidInputException;
import backend.academy.hangman.model.GameModel;
import backend.academy.hangman.model.data.WordsRepository;
import backend.academy.hangman.model.data.impl.GameLevel;
import backend.academy.hangman.util.SourceLoader;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameModelImpl implements GameModel {

    private GameLevel level = GameLevel.EASY;
    private String category;
    private final WordsRepository wordsRepository;
    private SessionState sessionState;
    private final int attempts;
    private GameState gameState = GameState.MAIN_MENU;

    public GameModelImpl(String sourceFilePath, int attempts) {
        this.wordsRepository = SourceLoader.load(sourceFilePath);
        this.attempts = attempts;
    }

    @Override
    public List<String> getCategories() {
        return wordsRepository.getCategories();
    }

    @Override
    public SessionDTO start() {
        sessionState = new SessionState(
            category == null ? wordsRepository.getRandomCategoryWord(level) : wordsRepository.getWord(level, category),
            attempts);
        log.debug("Word: {}", sessionState.word());
        return mapStateToSession(sessionState);
    }

    @Override
    public SessionDTO process(Character input) {
        if (sessionState == null) {
            log.error("Game has not been started, process should be called only after start ");
            throw new IllegalStateException("Game has not been started");
        }
        return mapStateToSession(sessionState.process(input));
    }

    @Override
    public boolean gameIsWon() {
        return sessionState.gameIsWon();
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public GameModel updateGameState(int input) throws InvalidInputException {
        gameState = switch (gameState) {
            case MAIN_MENU -> processMainMenuState(input);
            case DIFFICULTY -> processDifficultyState(input);
            case CATEGORY -> processCategoryState(input);
            case null, default -> throw new IllegalStateException("Unexpected value: " + gameState);
        };
        if (gameState == GameState.CLOSE) {
            System.exit(0);
        }
        return this;
    }

    private GameState processCategoryState(int input) throws InvalidInputException {
        try {
            category = getCategories().get(input - 1);
            return GameState.MAIN_MENU;
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidInputException("Invalid command");
        }
    }

    private GameState processDifficultyState(int input) throws InvalidInputException {
        level = switch (input) {
            case 1 -> GameLevel.EASY;
            case 2 -> GameLevel.MEDIUM;
            case 3 -> GameLevel.HIGH;
            default -> throw new InvalidInputException("Invalid command");
        };
        return GameState.MAIN_MENU;
    }

    private GameState processMainMenuState(int input) throws InvalidInputException {
        return switch (input) {
            case 1 -> GameState.GAME;
            case 2 -> GameState.DIFFICULTY;
            case 3 -> GameState.CATEGORY;
            case 4 -> GameState.CLOSE;
            default -> throw new InvalidInputException("Invalid command");
        };
    }

    private SessionDTO mapStateToSession(SessionState sessionState) {
        return new SessionDTO(sessionState.word(), sessionState.hint(), sessionState.attempts(),
            sessionState.usedChars());
    }
}
