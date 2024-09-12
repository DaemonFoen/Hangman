package backend.academy;

import backend.academy.hangman.controller.GameControllerImpl;
import backend.academy.hangman.model.GameModelImpl;
import backend.academy.hangman.view.HangmanView;
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
        GameModelImpl gameModel = new GameModelImpl("src/main/resources/words.json", 6);
        Terminal terminal = TerminalBuilder.terminal();
        LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
        PrintWriter writer = terminal.writer();
        HangmanView game2 = new HangmanView(terminal, lineReader, writer);
        GameControllerImpl gc = new GameControllerImpl(gameModel, game2);
        try {
            while (gc.start())
                ;
        } catch (RuntimeException e) {

        }
    }
}
