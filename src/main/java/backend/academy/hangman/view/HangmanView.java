package backend.academy.hangman.view;

import backend.academy.hangman.controller.GameState;
import backend.academy.hangman.controller.Session;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

@Log4j2
public class HangmanView implements View {

    private final Terminal terminal;

    private final LineReader lineReader;

    private final PrintWriter writer;

    public HangmanView() {
        try {
            terminal = TerminalBuilder.terminal();
            lineReader = LineReaderBuilder.builder().terminal(terminal).build();
            writer = terminal.writer();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {

            int gameFildLenght = 57;

            int attempts = 6;

//            System.out.println("""
//            #################################################
//            #                                               #
//            #                === Hangman Game ===           #
//            #                                               #
//            """ + stages[0] + """
//            #                                               #
//            #          Word: """ + "getWordDisplay()" + """
//            #
//            #                                               #
//            """+drawAlphabetDisplay()+"""
//            #                                               #
//            #          Incorrect Guesses: """ + "incorrectGuesses + / + MAX_ATTEMPTS" + """
//            #                                               #
//            #################################################
//            """);

//            System.out.println(menu);

//            String choice = lineReader.readLine();
//
//            switch (choice) {
//                case "1":
//                    startGame();
//                    break;
//                case "2":
//                    chooseDifficulty();
//                    break;
//                case "3":
//                    System.out.println("Exiting the game...");
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
        }
    }

    private static String drawWord(String word, Set<Character> usedLetters) {
        StringBuilder template = new StringBuilder();
        template.append("#");
        int maxSize = 55;
        int currSize = 55 - word.length() * 2;
        template.append(" ".repeat(currSize / 2));

        for (char letter : word.toCharArray()) {
            if (usedLetters.contains(letter)) {
                template.append(letter).append(' ');
            } else {
                template.append("_ ");
            }
        }
        template.append(" ".repeat(currSize - currSize / 2)).append("#\n");
        return template.toString();
    }

    private static String drawAlphabetDisplay(Set<Character> usedLetters) {
        Character[] alphabet = new Character[26];
        for (int i = 0; i < 26; i++) {
            alphabet[i] = Character.valueOf((char) ('A' + i));
        }
        StringBuilder template = new StringBuilder();

        template.append("#");
        template.append(" ".repeat(15));

        for (int i = 0; i < 26; i++) {
            template.append(usedLetters.contains(alphabet[i]) ? "_" : alphabet[i]);
            template.append(" ");

            if (i == 12) {
                template.append(" ".repeat(14)).append("#").append("\n").append("#").append(" ".repeat(14));
            }
        }
        template.append(" ".repeat(15)).append("#").append("\n");
        return template.toString();
    }

    private static String drawAttempts(int attemots) {
        int maxSize = 55;
        StringBuilder template = new StringBuilder();
        template.append("#").append(" ".repeat(21)).append("Attemps: ").append(attemots).append(" ".repeat(24))
            .append("#\n");
        return template.toString();
    }

    private static String drawRetry(boolean flag) {
        if (flag == false) {
            return "";
        }
        int maxSize = 55;
        StringBuilder template = new StringBuilder();
        template.append("#").append(" ".repeat(21)).append("Retry?(y/n)").append(" ".repeat(23)).append("#\n");
        return template.toString();
    }

    private void clearScreen() {
        terminal.puts(Capability.clear_screen);
        terminal.flush();
    }

    @Override
    public int draw(GameState state) {
        while (true) {
            switch (state) {
                case MAIN_MENU -> writer.println(Screens.menu);
                case DIFFICULTY -> writer.println(Screens.difficultyMenu);
                case CATEGORY -> writer.println(Screens.categoryTemplate);
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
    public Character draw(Session session) {
        String[] stages = {
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |                                  #
            #                    |                                  #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |                                  #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |   |                              #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|                              #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|\\                            #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|\\                            #
            #                    |  /                               #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|\\                            #
            #                    |  / \\                            #
            #                    |                                  #
            #                  ======                               #
            """
        };

        String gameTemplate = """
            #########################################################
            #                                                       #
            #                === Hangman Game ===                   #
            #                                                       #
            """ + stages[6 - session.attempts()] + """
            #                                                       #
            """ + drawWord(session.word(), session.usedChars()) +
            """
                #                                                       #
                """ + drawAlphabetDisplay(session.usedChars()) +
            """
                #                                                       #
                """ + drawAttempts(session.attempts()) +
            """
                #                                                       #
                """ + drawRetry(false) +
            """
                #                                                       #
                #########################################################
                """;

        writer.println(gameTemplate);

        while (true) {
            String ans = lineReader.readLine();
            if (ans.length() == 1 && Character.isLetter(ans.toCharArray()[0])) {
                if (!session.usedChars().contains(Character.toUpperCase(ans.toCharArray()[0]))) {
                    return Character.toUpperCase(ans.toCharArray()[0]);
                } else {
                    writer.println(gameTemplate);
                    printError("Letter is already in use");
                }
            } else {
                writer.println(gameTemplate);
                printError("Incorrect answer. Try again.");
            }
        }
    }

    @Override
    public Character drawRetry(Session session) {
        String[] stages = {
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |                                  #
            #                    |                                  #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |                                  #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |   |                              #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|                              #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|\\                            #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|\\                            #
            #                    |  /                               #
            #                    |                                  #
            #                  ======                               #
            """,
            """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|\\                            #
            #                    |  / \\                            #
            #                    |                                  #
            #                  ======                               #
            """
        };

        String gameTemplate = """
            #########################################################
            #                                                       #
            #                === Hangman Game ===                   #
            #                                                       #
            """ + stages[6 - session.attempts()] + """
            #                                                       #
            """ + drawWord(session.word(), session.usedChars()) +
            """
                #                                                       #
                """ + drawAlphabetDisplay(session.usedChars()) +
            """
                #                                                       #
                """ + drawAttempts(session.attempts()) +
            """
                #                                                       #
                """ + drawRetry(true) +
            """
                #                                                       #
                #########################################################
                """;

        while (true) {
            String ans = lineReader.readLine();
            if (ans.length() == 1 && Character.isLetter(
                ans.toCharArray()[0]) && (Character.toUpperCase(ans.toCharArray()[0]) == 'Y'
                || Character.toUpperCase(ans.toCharArray()[0]) == 'N')) {
                return Character.toUpperCase(ans.toCharArray()[0]);
            } else {
                writer.println(gameTemplate);
                printError("Incorrect answer. Try again.");
            }
        }

    }

    @Override
    public void printError(String error) {
        writer.println(error);
    }
}
