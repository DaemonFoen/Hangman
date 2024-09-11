package hangman.controller;

import backend.academy.hangman.controller.GameControllerImpl;
import backend.academy.hangman.controller.Session;
import backend.academy.hangman.model.api.GameModel;
import backend.academy.hangman.view.View;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerImplTest {

    @Mock
    private GameModel gameModel;

    @Mock
    private View view;

    @InjectMocks
    private GameControllerImpl gameController;

    @Test
    void testStartGameWin() {
        Session sessionMock = mock(Session.class);
        when(gameModel.start(any(), any())).thenReturn(sessionMock);
        when(sessionMock.attempts()).thenReturn(1);
        when(gameModel.gameIsWon()).thenReturn(true);
        when(view.drawMenu(any(), any())).thenReturn(1);
        when(view.drawEnd(any(), eq(true))).thenReturn('Y');

        boolean result = gameController.start();

        assertTrue(result);
        verify(gameModel).start(any(), any());
        verify(view).drawEnd(any(), eq(true));
    }

    @Test
    void testStartGameLose() {
        Session sessionMock = mock(Session.class);
        when(gameModel.start(any(), any())).thenReturn(sessionMock);
        when(sessionMock.attempts()).thenReturn(0);
        when(gameModel.gameIsWon()).thenReturn(false);
        when(view.drawMenu(any(), any())).thenReturn(1);
        when(view.drawEnd(any(), eq(false))).thenReturn('n');

        boolean result = gameController.start();

        assertFalse(result);
        verify(gameModel).start(any(), any());
        verify(view).drawEnd(any(), eq(false));
    }

}
