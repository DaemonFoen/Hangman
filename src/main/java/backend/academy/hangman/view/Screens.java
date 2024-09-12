package backend.academy.hangman.view;

import backend.academy.hangman.controller.Session;
import java.util.List;
import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("all")
public class Screens {

    private static final String[] STAGES = {
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
            #                    |  /|\\                             #
            #                    |                                  #
            #                    |                                  #
            #                  ======                               #
            """,
        """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|\\                             #
            #                    |  /                               #
            #                    |                                  #
            #                  ======                               #
            """,
        """
            #                    +---+                              #
            #                    |   |                              #
            #                    |   O                              #
            #                    |  /|\\                             #
            #                    |  / \\                             #
            #                    |                                  #
            #                  ======                               #
            """
    };

    public static final String MENU = """
        #########################################################
        #                                                       #
        #                === Hangman Game ===                   #
        #                                                       #
        #                 1. Start Game                         #
        #                --------------------                   #
        #                 2. Choose Difficulty                  #
        #                --------------------                   #
        #                 3. Choose Words category              #
        #                --------------------                   #
        #                 4. Exit                               #
        #                                                       #
        #                                                       #
        #                                                       #
        #########################################################
        """;

    public static final String DIFFICULT_MENU = """
        #########################################################
        #                                                       #
        #                === Hangman Game ===                   #
        #                                                       #
        #                 1. Easy                               #
        #                --------------------                   #
        #                 2. Medium                             #
        #                --------------------                   #
        #                 3. Hard                               #
        #                                                       #
        #                                                       #
        #        Select your difficulty(Easy by default):       #
        #########################################################
        """;

    public static String getCategoryMenu(List<String> categories) {
        return """
            #########################################################
            #                                                       #
            #              === Hangman Categories ===               #
            #                                                       #
            #          Please select a category:                    #
            #                                                       #
                    """ + drawCategories(categories) +

            """
                #                                                       #
                #########################################################
                """;
    }

    public static String getGameView(Session session) {
        return """
            #########################################################
            #                                                       #
            #                === Hangman Game ===                   #
            #                                                       #
            """ + STAGES[STAGES.length - 1 - session.attempts()] + """
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
                """ + (session.attempts() > 3 ? "" : drawHint(session.hint())) +
            """
                #                                                       #
                #########################################################
                """;
    }

    public static String getEndView(Session session, boolean isWin) {
        return """
            #########################################################
            #                                                       #
            #                === Hangman Game ===                   #
            #                                                       #
            """ + STAGES[STAGES.length - 1 - session.attempts()] + """
            #                                                       #
            """ + drawWord(session.word(), session.usedChars())
            +
            """
                #                                                       #
                """ + drawAlphabetDisplay(session.usedChars())
            +
            """
                #                                                       #
                """ + drawAttempts(session.attempts())
            +
            """
                #                                                       #
                """ + drawEnding(isWin)
            +
            """
                #                                                       #
                #########################################################
                """;
    }

    private static String drawWord(String word, Set<Character> usedLetters) {
        StringBuilder template = new StringBuilder();
        template.append("#");
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
            alphabet[i] = (char) ('A' + i);
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

    private static String drawAttempts(int attempts) {
        return "#" + " ".repeat(21) + "Attempts: " + attempts + " ".repeat(23)
            + "#\n";
    }

    private static String drawEnding(boolean isWin) {
        if (isWin) {
            return "#" + " ".repeat(14) + "You win, play again?(y/n)" + " ".repeat(16) + "#\n";
        } else {
            return "#" + " ".repeat(21) + "Retry?(y/n)" + " ".repeat(23) + "#\n";
        }
    }

    private static String drawHint(String hint) {
        int totalWidth = 57;
        int paddingSpace = totalWidth - 8;
        StringBuilder result = new StringBuilder();
        int start = 0;
        while (start < hint.length()) {
            int end = Math.min(start + paddingSpace, hint.length());
            String line = hint.substring(start, end);
            int paddingRight = paddingSpace - line.length();
            result.append("#    ").append(line).append(" ".repeat(paddingRight)).append("  #\n");
            start += paddingSpace;
        }

        return result.toString();
    }

    public static String drawCategories(List<String> categories) {
        int size = 42;
        StringBuilder template = new StringBuilder();

        for (int i = 0; i < categories.size(); i++) {
            template.append("#").append(" ".repeat(10)).append("%d. %s".formatted(i + 1, categories.get(i)))
                .append(" ".repeat(size - categories.get(i).length())).append("#\n");
        }
        return template.toString();
    }
}
