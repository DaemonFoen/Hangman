package backend.academy.hangman.model.data;

import java.util.List;
import static backend.academy.hangman.util.RandomGenerator.getRandomNumber;

record Category(List<Word> easy, List<Word> medium, List<Word> hard) {

    Word getRandomWordFromCategory(GameLevel level) {
        return switch (level) {
            case EASY -> easy.get(getRandomNumber() % easy.size());
            case MEDIUM -> medium.get(getRandomNumber() % medium.size());
            case HIGH -> hard.get(getRandomNumber() % hard.size());
        };
    }
}
