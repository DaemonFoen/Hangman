package backend.academy.hangman.model.api;

import backend.academy.hangman.controller.Session;
import backend.academy.hangman.model.data.GAME_LEVEL;
import java.util.List;

public interface GameModel {

    List<String> getCategories();

    Session start(GAME_LEVEL level, String category);

    Session process(Character input);
}
