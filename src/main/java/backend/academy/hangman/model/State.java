package backend.academy.hangman.model;

import backend.academy.hangman.model.data.Word;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class State {

    private final String word;
    private final String hint;
    private final Set<Character> wordCharacters;
    private int attempts = 6;
    private final Set<Character> usedChars = new HashSet<>();

    public State(Word word) {
        this.word = word.word().toUpperCase();
        this.hint = word.hint();
        this.wordCharacters = getWordCharacters(this.word.toCharArray());
    }

    public State process(Character answer) {
        usedChars.add(answer);
        if (!wordContainChar(answer)) {
            attempts--;
        }
        return this;
    }

    public boolean gameIsWon() {
        return wordCharacters.equals(usedChars);
    }

    private boolean wordContainChar(Character ch) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ch) {
                return true;
            }
        }
        return false;
    }

    private Set<Character> getWordCharacters(char[] chars) {
        Set<Character> characters = new HashSet<>();
        for (var ch : chars) {
            characters.add(ch);
        }
        return characters;
    }
}
