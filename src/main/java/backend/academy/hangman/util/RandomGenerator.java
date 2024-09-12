package backend.academy.hangman.util;

import java.security.SecureRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static int getRandomNumber() {
        try {
            return Math.absExact(RANDOM.nextInt());
        } catch (ArithmeticException e) {
            return Integer.MAX_VALUE;
        }
    }
}
