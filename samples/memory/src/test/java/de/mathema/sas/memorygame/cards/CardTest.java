package de.mathema.sas.memorygame.cards;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CardTest {

    @Mock
    CardView cardView;

    @Test
    public void testMatches_SameCard() {
        Card firstCard = Card.of(Card.Rank.ACE, Card.Suit.SPADE);

        assertEquals(false, firstCard.matches(firstCard));
    }

    @Test
    public void testMatches_DifferentSuites() {
        Card firstCard = Card.of(Card.Rank.ACE, Card.Suit.SPADE);
        Card secondCard = Card.of(Card.Rank.ACE, Card.Suit.DIAMOND);

        assertEquals(false, firstCard.matches(secondCard));
    }

    @Test
    public void testMatches_DifferentRanks() {
        Card firstCard = Card.of(Card.Rank.ACE, Card.Suit.SPADE);
        Card secondCard = Card.of(Card.Rank.KING, Card.Suit.SPADE);

        assertEquals(false, firstCard.matches(secondCard));
    }

    @Test
    public void testMatches_DifferentCards() {
        Card firstCard = Card.of(Card.Rank.ACE, Card.Suit.HEART);
        Card secondCard = Card.of(Card.Rank.KING, Card.Suit.SPADE);

        assertEquals(false, firstCard.matches(secondCard));
    }

    @Test
    public void testMatches_SameCards() {
        Card firstCard = Card.of(Card.Rank.KING, Card.Suit.SPADE);
        Card secondCard = Card.of(Card.Rank.KING, Card.Suit.SPADE);

        assertEquals(true, firstCard.matches(secondCard));
    }

    @Test
    public void cardInitiallyHidden() {
        Card card = Card.of(Card.Rank.KING, Card.Suit.SPADE);
        card.show(cardView);
        verify(cardView).hideCard();
    }

    @Test
    public void testHiddenCardRemainsHidden() {
        Card card = Card.of(Card.Rank.KING, Card.Suit.SPADE);
        card.turnFaceDown();
        card.show(cardView);
        verify(cardView).hideCard();
    }

    @Test
    public void testShowCard() {
        Card card = Card.of(Card.Rank.KING, Card.Suit.SPADE);

        card.turnFaceUp();
        card.show(cardView);
        verify(cardView).revealCard(Card.Rank.KING, Card.Suit.SPADE);

        card.turnFaceDown();
        card.show(cardView);
        verify(cardView).hideCard();
    }

    @Test
    public void testFreezeCard() {
        Card card = Card.of(Card.Rank.KING, Card.Suit.SPADE);
        assertEquals(true, card.isInPlay());

        card.freezeFaceUp();
        card.show(cardView);
        verify(cardView, times(1)).revealCard(Card.Rank.KING, Card.Suit.SPADE);
        assertEquals(false, card.isInPlay());

        card.turnFaceDown();
        card.show(cardView);
        verify(cardView, times(2)).revealCard(Card.Rank.KING, Card.Suit.SPADE);
        assertEquals(false, card.isInPlay());
    }
}
