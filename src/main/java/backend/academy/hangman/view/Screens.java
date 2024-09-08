package backend.academy.hangman.view;

import java.util.List;

public class Screens {

    public static final String menu = """
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

    public static final String difficultyMenu = """
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

    public static final String categoryTemplate = """
        #########################################################
        #                                                       #
        #              === Hangman Categories ===               #
        #                                                       #
        #          Please select a category:                    #
        #                                                       #
        """ + drawCategories(List.of("Animals", "A", "B", "tdtp")) +

        """
            #                                                       #
            #########################################################
             """;

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
