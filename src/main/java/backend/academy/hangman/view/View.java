package backend.academy.hangman.view;

import backend.academy.hangman.controller.GameState;
import backend.academy.hangman.controller.Session;

public interface View {

    int draw(GameState state);

    Character draw(Session session);

    Character drawRetry(Session session);

    void printError(String error);
}
