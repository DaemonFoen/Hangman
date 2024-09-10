package backend.academy.hangman.view;

import backend.academy.hangman.controller.GameState;
import backend.academy.hangman.controller.Session;
import java.util.List;

public interface View {

    int draw(GameState state, List<String> categorise);

    Character draw(Session session);

    Character drawEnd(Session session, boolean isWin);

    void printError(String error);
}
