package backend.academy.hangman.util;

import java.security.SecureRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static int getRandomNumber() {
        try {
            return Math.absExact(random.nextInt());
        } catch (ArithmeticException e) {
            return 42;
        }
    }
}
