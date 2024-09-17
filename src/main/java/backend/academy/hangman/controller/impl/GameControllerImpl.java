package backend.academy.hangman.controller.impl;

import backend.academy.hangman.controller.GameController;
import backend.academy.hangman.exception.InvalidInputException;
import backend.academy.hangman.model.GameModel;
import backend.academy.hangman.view.View;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameControllerImpl implements GameController {

    private final GameModel gameModel;
    private final View view;

    public GameControllerImpl(GameModel gameModel, View view) {
        this.gameModel = gameModel;
        this.view = view;
    }

    private void menu() {
        while (gameModel.getGameState() != GameState.GAME) {
            int playerCommand = view.drawMenu(gameModel.getGameState(), gameModel.getCategories());
            try {
                gameModel.updateGameState(playerCommand);
            } catch (InvalidInputException e) {
                view.printError(e.getMessage());
            }
        }
    }

    @Override
    public boolean start() {
        menu();
        SessionDTO sessionDTO = gameModel.start();

        while (sessionDTO.attempts() != 0 && !gameModel.gameIsWon()) {
            sessionDTO = gameModel.process(view.drawGame(sessionDTO));
            log.debug("Game status: win: {}, used chars: {}", gameModel.gameIsWon(), sessionDTO.usedChars());
        }

        return 'Y' == (gameModel.gameIsWon() ? view.drawEnd(sessionDTO, true) : view.drawEnd(sessionDTO, false));
    }
}
