package hangman.view;

import backend.academy.hangman.controller.GameState;
import backend.academy.hangman.controller.Session;
import backend.academy.hangman.view.HangmanView;
import backend.academy.hangman.view.Screens;
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
class HangmanViewTest {

    @SuppressWarnings("all")
    @Mock
    Terminal terminal;

    @Mock
    private LineReader lineReader;

    @Mock
    private PrintWriter writer;

    @InjectMocks
    private HangmanView hangmanView;

    @Test
    void testDrawMenuMainMenu() {
        when(lineReader.readLine()).thenReturn("1");
        List<String> categories = List.of("Category1", "Category2");

        hangmanView.drawMenu(GameState.MAIN_MENU, categories);

        verify(writer).println(Screens.MENU);
    }

    @Test
    void testDrawMenuInvalidInput() {
        when(lineReader.readLine()).thenReturn("invalid", "2");
        GameState state = GameState.MAIN_MENU;
        List<String> categories = List.of("Category1", "Category2");

        int result = hangmanView.drawMenu(state, categories);

        assertEquals(2, result);
        verify(writer, times(1)).println("Please enter a number");
        verify(writer, times(2)).println(Screens.MENU);
    }

    @Test
    void testDrawGameValidInput() {
        Session sessionMock = mock(Session.class);
        when(sessionMock.word()).thenReturn("ABOBA");
        when(sessionMock.attempts()).thenReturn(6);
        when(sessionMock.usedChars()).thenReturn(Set.of('A', 'B'));
        when(lineReader.readLine()).thenReturn("C");

        char result = hangmanView.drawGame(sessionMock);

        assertEquals('C', result);
        verify(writer).println(Screens.getGameView(sessionMock));
    }

    @Test
    void testDrawGameInvalidInput() {
        Session sessionMock = mock(Session.class);
        when(sessionMock.word()).thenReturn("ABOBA");
        when(sessionMock.attempts()).thenReturn(6);
        when(sessionMock.usedChars()).thenReturn(Set.of('A', 'B'));
        when(lineReader.readLine()).thenReturn("A", "C");

        char result = hangmanView.drawGame(sessionMock);

        assertEquals('C', result);
        verify(writer, times(1)).println("Letter is already in use");
        verify(writer, times(2)).println(Screens.getGameView(sessionMock));
    }

    @Test
    void testDrawEndWin() {
        Session sessionMock = mock(Session.class);
        when(sessionMock.word()).thenReturn("ABOBA");
        when(sessionMock.attempts()).thenReturn(6);
        when(lineReader.readLine()).thenReturn("Y");

        char result = hangmanView.drawEnd(sessionMock, true);

        assertEquals('Y', result);
        verify(writer).println(Screens.getEndView(sessionMock, true));
    }

    @Test
    void testDrawEndLose() {
        Session sessionMock = mock(Session.class);
        when(sessionMock.word()).thenReturn("ABOBA");
        when(sessionMock.attempts()).thenReturn(6);
        when(lineReader.readLine()).thenReturn("Y");

        char result = hangmanView.drawEnd(sessionMock, false);

        assertEquals('Y', result);
        verify(writer).println(Screens.getEndView(sessionMock, false));
    }

    @Test
    void testDrawEndInvalidInput() {
        Session sessionMock = mock(Session.class);
        when(sessionMock.word()).thenReturn("ABOBA");
        when(sessionMock.attempts()).thenReturn(6);
        when(lineReader.readLine()).thenReturn("invalid", "Y");

        char result = hangmanView.drawEnd(sessionMock, true);

        assertEquals('Y', result);
        verify(writer, times(1)).println("Incorrect answer. Try again.");
        verify(writer, times(2)).println(Screens.getEndView(sessionMock, true));
    }

    @Test
    void testPrintError() {
        hangmanView.printError("Test error");

        verify(writer).println("Test error");
    }
}
