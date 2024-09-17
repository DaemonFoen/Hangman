package backend.academy.hangman.view;

import backend.academy.hangman.controller.impl.GameState;
import backend.academy.hangman.controller.impl.SessionDTO;
import java.util.List;

public interface View {

    int drawMenu(GameState state, List<String> categorise);

    Character drawGame(SessionDTO sessionDTO);

    Character drawEnd(SessionDTO sessionDTO, boolean isWin);

    void printError(String error);
}
