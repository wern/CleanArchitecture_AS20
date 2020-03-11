package de.mathema.sas.memorygame.rules;

import de.mathema.sas.memorygame.cards.Card;
import de.mathema.sas.memorygame.player.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    Game game;

    @Mock
    GameView gameView;

    @Mock
    Player firstPlayer;
    @Mock
    Player secondPlayer;

    @Mock
    Card firstSelectedCard;
    @Mock
    Card secondSelectedCard;

    @Before
    public void init() {
        game = Mockito.spy(new Game(gameView, firstPlayer, secondPlayer));

        when(gameView.selectCard(any(), any()))
                .thenReturn(firstSelectedCard)
                .thenReturn(secondSelectedCard);
    }

    @Test
    public void testStartRound_CardsMatch() {
        // stops the game after one round
        doReturn(true).when(game).noMoreCards(any());
        when(firstSelectedCard.matches(secondSelectedCard)).thenReturn(true);

        game.start();

        verify(firstPlayer).scorePoint();
        verify(secondPlayer, times(0)).scorePoint();

        verify(firstSelectedCard).turnFaceUp();
        verify(firstSelectedCard).freezeFaceUp();
        verify(secondSelectedCard).turnFaceUp();
        verify(secondSelectedCard).freezeFaceUp();
    }

    @Test
    public void testStartRound_CardsDontMatch() {
        // stops the game after one round
        doReturn(true).when(game).noMoreCards(any());
        when(firstSelectedCard.matches(secondSelectedCard)).thenReturn(false);

        game.start();

        verify(firstPlayer, times(0)).scorePoint();
        verify(secondPlayer, times(0)).scorePoint();

        verify(firstSelectedCard).turnFaceUp();
        verify(firstSelectedCard).turnFaceDown();
        verify(secondSelectedCard).turnFaceUp();
        verify(secondSelectedCard).turnFaceDown();
    }

    @Test
    public void testStartRound_ChangePlayersEachRound() {
        // stops the game after two rounds
        doReturn(false).
                doReturn(true)
                .when(game).noMoreCards(any());
        when(firstSelectedCard.matches(secondSelectedCard)).thenReturn(false);

        game.start();

        verify(game, times(1)).startRound(firstPlayer);
        verify(game, times(1)).startRound(secondPlayer);
    }

    @Test
    public void testNextPlayer_CardsMatch_SamePlayerPlaysOn() {
        when(firstSelectedCard.matches(secondSelectedCard)).thenReturn(true);
        Player nextPlayer = game.nextPlayer(firstPlayer, firstSelectedCard, secondSelectedCard);
        assertEquals(firstPlayer, nextPlayer);

        nextPlayer = game.nextPlayer(secondPlayer, firstSelectedCard, secondSelectedCard);
        assertEquals(secondPlayer, nextPlayer);
    }

    @Test
    public void testNextPlayer_CardsDontMatch_TheOtherPlayerIsOn() {
        when(firstSelectedCard.matches(secondSelectedCard)).thenReturn(false);
        Player nextPlayer = game.nextPlayer(firstPlayer, firstSelectedCard, secondSelectedCard);
        assertEquals(secondPlayer, nextPlayer);

        nextPlayer = game.nextPlayer(secondPlayer, firstSelectedCard, secondSelectedCard);
        assertEquals(firstPlayer, nextPlayer);
    }

    @Test
    public void testTurnCardsFaceDown() {
        Card firstCard = Mockito.mock(Card.class);
        Card secondCard = Mockito.mock(Card.class);

        game.turnCardsFaceDown(firstCard, secondCard);
        verify(firstCard).turnFaceDown();
        verify(secondCard).turnFaceDown();
        verify(gameView).update(any(), any());
    }

    @Test
    public void testTurnCardsFaceUp() {
        Card firstCard = Mockito.mock(Card.class);
        Card secondCard = Mockito.mock(Card.class);

        game.turnCardsFaceUp(firstCard, secondCard);
        verify(firstCard).turnFaceUp();
        verify(secondCard).turnFaceUp();
        verify(gameView).update(any(), any());
    }

    @Test
    public void testFreezeCards() {
        Card firstCard = Mockito.mock(Card.class);
        Card secondCard = Mockito.mock(Card.class);

        game.freezeCards(firstCard, secondCard);
        verify(firstCard).freezeFaceUp();
        verify(secondCard).freezeFaceUp();
        verify(gameView).update(any(), any());
    }

    @Test
    public void finishGame_PlayerOneWins() {
        when(firstPlayer.outscores(secondPlayer)).thenReturn(true);
        game.finishGame();

        verify(firstPlayer).increaseRating();
        verify(secondPlayer).reduceRating();
        verify(gameView).winner(firstPlayer);
    }

    @Test
    public void finishGame_PlayerOneLooses() {
        when(firstPlayer.outscores(secondPlayer)).thenReturn(false);
        game.finishGame();

        verify(firstPlayer).reduceRating();
        verify(secondPlayer).increaseRating();
        verify(gameView).winner(secondPlayer);
    }

    @Test
    public void createDuplicateCards() {
        ArgumentCaptor<List<Card>> captureCards = ArgumentCaptor.forClass(List.class);
        game.updateBoard();
        verify(gameView).update(captureCards.capture(), any());

        List<Card> cards = captureCards.getValue();
        Set<Card> uniqueCards = new HashSet<>(cards);

        assertEquals(uniqueCards.size(), cards.size() / 2);
    }

    @Test
    public void noCardsInPlay_GameFinished() {
        when(firstSelectedCard.isInPlay()).thenReturn(false);
        when(secondSelectedCard.isInPlay()).thenReturn(false);

        assertEquals(true, game.noMoreCards(List.of(firstSelectedCard, secondSelectedCard)));
    }

    @Test
    public void cardsStillInPlay_GameNotFinished() {
        when(firstSelectedCard.isInPlay()).thenReturn(true);

        assertEquals(false, game.noMoreCards(List.of(firstSelectedCard, secondSelectedCard)));
    }
}
