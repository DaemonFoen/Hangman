package hangman.view;

import backend.academy.hangman.controller.impl.GameState;
import backend.academy.hangman.controller.impl.SessionDTO;
import backend.academy.hangman.view.impl.HangmanDrawUtils;
import backend.academy.hangman.view.impl.HangmanViewImpl;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HangmanViewImplTest {

    @SuppressWarnings("all")
    @Mock
    Terminal terminal;

    @Mock
    private LineReader lineReader;

    @Mock
    private PrintWriter writer;

    @InjectMocks
    private HangmanViewImpl hangmanViewImpl;

    @Test
    void testDrawMenuMainMenu() {
        when(lineReader.readLine()).thenReturn("1");
        List<String> categories = List.of("Category1", "Category2");

        hangmanViewImpl.drawMenu(GameState.MAIN_MENU, categories);

        verify(writer).println(HangmanDrawUtils.MENU);
    }

    @Test
    void testDrawMenuInvalidInput() {
        when(lineReader.readLine()).thenReturn("invalid", "2");
        GameState state = GameState.MAIN_MENU;
        List<String> categories = List.of("Category1", "Category2");

        int result = hangmanViewImpl.drawMenu(state, categories);

        assertEquals(2, result);
        verify(writer, times(1)).println("Please enter a number");
        verify(writer, times(2)).println(HangmanDrawUtils.MENU);
    }

    @Test
    void testDrawGameValidInput() {
        SessionDTO sessionDTOMock = mock(SessionDTO.class);
        when(sessionDTOMock.word()).thenReturn("ABOBA");
        when(sessionDTOMock.attempts()).thenReturn(6);
        when(sessionDTOMock.usedChars()).thenReturn(Set.of('A', 'B'));
        when(lineReader.readLine()).thenReturn("C");

        char result = hangmanViewImpl.drawGame(sessionDTOMock);

        assertEquals('C', result);
        verify(writer).println(HangmanDrawUtils.getGameView(sessionDTOMock));
    }

    @Test
    void testDrawGameInvalidInput() {
        SessionDTO sessionDTOMock = mock(SessionDTO.class);
        when(sessionDTOMock.word()).thenReturn("ABOBA");
        when(sessionDTOMock.attempts()).thenReturn(6);
        when(sessionDTOMock.usedChars()).thenReturn(Set.of('A', 'B'));
        when(lineReader.readLine()).thenReturn("A", "C");

        char result = hangmanViewImpl.drawGame(sessionDTOMock);

        assertEquals('C', result);
        verify(writer, times(1)).println("Letter is already in use");
        verify(writer, times(2)).println(HangmanDrawUtils.getGameView(sessionDTOMock));
    }

    @Test
    void testDrawEndWin() {
        SessionDTO sessionDTOMock = mock(SessionDTO.class);
        when(sessionDTOMock.word()).thenReturn("ABOBA");
        when(sessionDTOMock.attempts()).thenReturn(6);
        when(lineReader.readLine()).thenReturn("Y");

        char result = hangmanViewImpl.drawEnd(sessionDTOMock, true);

        assertEquals('Y', result);
        verify(writer).println(HangmanDrawUtils.getEndView(sessionDTOMock, true));
    }

    @Test
    void testDrawEndLose() {
        SessionDTO sessionDTOMock = mock(SessionDTO.class);
        when(sessionDTOMock.word()).thenReturn("ABOBA");
        when(sessionDTOMock.attempts()).thenReturn(6);
        when(lineReader.readLine()).thenReturn("Y");

        char result = hangmanViewImpl.drawEnd(sessionDTOMock, false);

        assertEquals('Y', result);
        verify(writer).println(HangmanDrawUtils.getEndView(sessionDTOMock, false));
    }

    @Test
    void testDrawEndInvalidInput() {
        SessionDTO sessionDTOMock = mock(SessionDTO.class);
        when(sessionDTOMock.word()).thenReturn("ABOBA");
        when(sessionDTOMock.attempts()).thenReturn(6);
        when(lineReader.readLine()).thenReturn("invalid", "Y");

        char result = hangmanViewImpl.drawEnd(sessionDTOMock, true);

        assertEquals('Y', result);
        verify(writer, times(1)).println("Incorrect answer. Try again.");
        verify(writer, times(2)).println(HangmanDrawUtils.getEndView(sessionDTOMock, true));
    }

    @Test
    void testPrintError() {
        hangmanViewImpl.printError("Test error");

        verify(writer).println("Test error");
    }
}
