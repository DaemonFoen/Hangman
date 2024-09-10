package backend.academy.hangman.controller;

import backend.academy.hangman.model.api.GameModel;
import backend.academy.hangman.model.data.GAME_LEVEL;
import backend.academy.hangman.view.View;
import lombok.Getter;
import lombok.Setter;

public class GameControllerImpl implements GameController {

    @Getter @Setter
    private GAME_LEVEL difficulty = GAME_LEVEL.EASY;
    private String category;
    private final GameModel gameModel;
    private final View view;

    public GameControllerImpl(GameModel gameModel, View view) {
        this.gameModel = gameModel;
        this.view = view;
    }

    private void init() {
        GameState state = GameState.MAIN_MENU;
        while (state != GameState.GAME) {
            int playerCommand = view.draw(state, gameModel.getCategories());
            switch (state) {
                case MAIN_MENU -> {
                    switch (playerCommand) {
                        case 1 -> state = GameState.GAME;
                        case 2 -> state = GameState.DIFFICULTY;
                        case 3 -> state = GameState.CATEGORY;
                        case 4 -> System.exit(0);
                        default -> view.printError("Invalid command");
                    }
                }
                case DIFFICULTY -> {
                    switch (playerCommand) {
                        case 1 -> {
                            difficulty = GAME_LEVEL.EASY;
                            state = GameState.MAIN_MENU;
                        }
                        case 2 -> {
                            difficulty = GAME_LEVEL.MEDIUM;
                            state = GameState.MAIN_MENU;
                        }
                        case 3 -> {
                            difficulty = GAME_LEVEL.HIGH;
                            state = GameState.MAIN_MENU;
                        }
                        default -> view.printError("Invalid command");
                    }
                }
                case GameState.CATEGORY -> {
                    try {
                        category = gameModel.getCategories().get(playerCommand - 1);
                        state = GameState.MAIN_MENU;
                    } catch (IndexOutOfBoundsException e) {
                        view.printError("Invalid command");
                    }
                }
                case null, default -> throw new IllegalStateException("Unexpected value: " + state);
            }
        }
    }

    public boolean start() {
        init();
        Session session = gameModel.start(difficulty, category);

        while (session.attempts() != 0 && !gameModel.gameIsWon()) {
            session = gameModel.process(view.draw(session));
        }

        return 'Y' == (gameModel.gameIsWon() ? view.drawEnd(session, true) : view.drawEnd(session, false));
    }
}
