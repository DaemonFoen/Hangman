package backend.academy;

import backend.academy.hangman.controller.GameController;
import backend.academy.hangman.controller.impl.GameControllerImpl;
import backend.academy.hangman.model.GameModel;
import backend.academy.hangman.model.impl.GameModelImpl;
import backend.academy.hangman.view.View;
import backend.academy.hangman.view.impl.HangmanViewImpl;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.experimental.UtilityClass;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

@UtilityClass
public class Main {

    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {
        GameModel gameModel = new GameModelImpl("src/main/resources/words.json", 6);
        Terminal terminal = TerminalBuilder.terminal();
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        PrintWriter writer = terminal.writer();
        View game = new HangmanViewImpl(terminal, lineReader, writer);
        GameController gc = new GameControllerImpl(gameModel, game);
        while (gc.start())
            ;
    }
}
