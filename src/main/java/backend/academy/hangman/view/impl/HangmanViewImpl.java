package backend.academy.hangman.view.impl;

import backend.academy.hangman.controller.impl.GameState;
import backend.academy.hangman.controller.impl.SessionDTO;
import backend.academy.hangman.view.View;
import java.io.PrintWriter;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp.Capability;

@Log4j2
public class HangmanViewImpl implements View {

    private final Terminal terminal;

    private final LineReader lineReader;

    private final PrintWriter writer;

    public HangmanViewImpl(Terminal terminal, LineReader lineReader, PrintWriter writer) {
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
                case MAIN_MENU -> writer.println(HangmanDrawUtils.MENU);
                case DIFFICULTY -> writer.println(HangmanDrawUtils.DIFFICULT_MENU);
                case CATEGORY -> writer.println(HangmanDrawUtils.getCategoryMenu(categories));
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
    public Character drawGame(SessionDTO sessionDTO) {
        writer.println(HangmanDrawUtils.getGameView(sessionDTO));

        while (true) {
            String ans = lineReader.readLine();
            if (ans.length() == 1 && Character.isLetter(ans.toCharArray()[0])) {
                if (!sessionDTO.usedChars().contains(Character.toUpperCase(ans.toCharArray()[0]))) {
                    return Character.toUpperCase(ans.toCharArray()[0]);
                } else {
                    writer.println(HangmanDrawUtils.getGameView(sessionDTO));
                    printError("Letter is already in use");
                }
            } else {
                writer.println(HangmanDrawUtils.getGameView(sessionDTO));
                printError("Incorrect answer, only letters are valid values. Try again.");
            }
        }
    }

    @Override
    public Character drawEnd(SessionDTO sessionDTO, boolean isWin) {
        writer.println(HangmanDrawUtils.getEndView(sessionDTO, isWin));

        while (true) {
            String ans = lineReader.readLine();
            if (ans.length() == 1 && Character.isLetter(
                ans.toCharArray()[0]) && (Character.toUpperCase(ans.toCharArray()[0]) == 'Y'
                || Character.toUpperCase(ans.toCharArray()[0]) == 'N')) {
                return Character.toUpperCase(ans.toCharArray()[0]);
            } else {
                writer.println(HangmanDrawUtils.getEndView(sessionDTO, isWin));
                printError("Incorrect answer. Try again.");
            }
        }
    }

    @Override
    public void printError(String error) {
        writer.println(error);
    }
}
