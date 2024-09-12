package backend.academy.hangman.view;

import backend.academy.hangman.controller.GameState;
import backend.academy.hangman.controller.Session;
import java.io.PrintWriter;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp.Capability;

@Log4j2
public class HangmanView implements View {

    private final Terminal terminal;

    private final LineReader lineReader;

    private final PrintWriter writer;

    public HangmanView(Terminal terminal, LineReader lineReader, PrintWriter writer) {
        this.terminal = terminal;
        this.lineReader = lineReader;
        this.writer = writer;
    }

    private void clearScreen() {
        terminal.puts(Capability.clear_screen);
        terminal.flush();
    }

    @Override
    public int drawMenu(GameState state, List<String> categories) {
        while (true) {
            switch (state) {
                case MAIN_MENU -> writer.println(Screens.MENU);
                case DIFFICULTY -> writer.println(Screens.DIFFICULT_MENU);
                case CATEGORY -> writer.println(Screens.getCategoryMenu(categories));
                default -> throw new IllegalStateException("State GAME cannot be in main menu");
            }
            try {
                int ans = Integer.parseInt(lineReader.readLine());
                clearScreen();
                return ans;
            } catch (NumberFormatException e) {
                clearScreen();
                printError("Please enter a number");
            }

        }
    }

    @Override
    public Character drawGame(Session session) {
        writer.println(Screens.getGameView(session));

        while (true) {
            String ans = lineReader.readLine();
            if (ans.length() == 1 && Character.isLetter(ans.toCharArray()[0])) {
                if (!session.usedChars().contains(Character.toUpperCase(ans.toCharArray()[0]))) {
                    return Character.toUpperCase(ans.toCharArray()[0]);
                } else {
                    writer.println(Screens.getGameView(session));
                    printError("Letter is already in use");
                }
            } else {
                writer.println(Screens.getGameView(session));
                printError("Incorrect answer, only letters are valid values. Try again.");
            }
        }
    }

    @Override
    public Character drawEnd(Session session, boolean isWin) {
        writer.println(Screens.getEndView(session, isWin));

        while (true) {
            String ans = lineReader.readLine();
            if (ans.length() == 1 && Character.isLetter(
                ans.toCharArray()[0]) && (Character.toUpperCase(ans.toCharArray()[0]) == 'Y'
                || Character.toUpperCase(ans.toCharArray()[0]) == 'N')) {
                return Character.toUpperCase(ans.toCharArray()[0]);
            } else {
                writer.println(Screens.getEndView(session, isWin));
                printError("Incorrect answer. Try again.");
            }
        }
    }

    @Override
    public void printError(String error) {
        writer.println(error);
    }
}
