package backend.academy.hangman.view;

import backend.academy.hangman.controller.GameState;
import backend.academy.hangman.controller.Session;
import java.util.List;

public interface View {

    int drawMenu(GameState state, List<String> categorise);

    Character drawGame(Session session);

    Character drawEnd(Session session, boolean isWin);

    void printError(String error);
}
