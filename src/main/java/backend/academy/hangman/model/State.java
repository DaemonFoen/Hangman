package backend.academy.hangman.model;

import backend.academy.hangman.model.data.Word;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class State {

    private final String word;
    private final String hint;
    private int attempts = 6;
    private final Set<Character> usedChars = new HashSet<>();

    public State(Word word) {
        this.word = word.word().toUpperCase();
        this.hint = word.hint();
    }

    public State process(Character answer) {
        usedChars.add(answer);
        if (!wordContainChar(answer)) {
            attempts--;
        }
        return this;
    }

    private boolean wordContainChar(Character ch) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ch) {
                return true;
            }
        }
        return false;
    }
}
