package backend.academy.hangman.model.api;

import backend.academy.hangman.controller.Session;
import backend.academy.hangman.model.data.GameLevel;
import java.util.List;

public interface GameModel {

    List<String> getCategories();

    Session start(GameLevel level, String category);

    Session process(Character input);

    boolean gameIsWon();
}
