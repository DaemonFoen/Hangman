package backend.academy.hangman.model;

import backend.academy.hangman.controller.impl.GameState;
import backend.academy.hangman.controller.impl.SessionDTO;
import backend.academy.hangman.exception.InvalidInputException;
import java.util.List;

public interface GameModel {

    List<String> getCategories();

    SessionDTO start();

    SessionDTO process(Character input);

    boolean gameIsWon();

    GameState getGameState();

    void updateGameState(int input) throws InvalidInputException;
}
