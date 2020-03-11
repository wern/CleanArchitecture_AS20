package de.mathema.sas.memorygame.rules;

import de.mathema.sas.memorygame.cards.Card;
import de.mathema.sas.memorygame.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private GameView gameView;
    private Player firstPlayer;
    private Player secondPlayer;

    private List<Card> cards;

    public Game(GameView gameView, Player firstPlayer, Player secondPlayer) {
        this.gameView = gameView;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.cards = prepareCardDeck();
    }

    List<Card> prepareCardDeck() {
        List<Card> playingCards = new ArrayList<>();

        for (Card.Rank rank : Card.Rank.values()) {
            playingCards.add(Card.of(rank, Card.Suit.SPADE));
            playingCards.add(Card.of(rank, Card.Suit.SPADE));
        }

        Collections.shuffle(playingCards);

        return playingCards;
    }

    public void start() {
        updateBoard();
        startRound(this.firstPlayer);
    }

    void updateBoard() {
        this.gameView.update(Collections.unmodifiableList(this.cards), List.of(firstPlayer, secondPlayer));
    }

    void startRound(Player player) {
        Card firstCard = this.gameView.selectCard(player, this.cards);
        Card secondCard = this.gameView.selectCard(player, this.cards);

        turnCardsFaceUp(firstCard, secondCard);
        givePlayerSomeTime();

        if (firstCard.matches(secondCard)) {
            player.scorePoint();
            freezeCards(firstCard, secondCard);
        } else {
            turnCardsFaceDown(firstCard, secondCard);
        }

        if (noMoreCards(this.cards)) {
            finishGame();
        } else {
            Player nextPlayer = nextPlayer(player, firstCard, secondCard);
            startRound(nextPlayer);
        }
    }

    boolean noMoreCards(List<Card> cards) {
        return cards.stream().noneMatch(Card::isInPlay);
    }

    void finishGame() {
        if (firstPlayer.outscores(secondPlayer)) {
            firstPlayer.increaseRating();
            secondPlayer.reduceRating();
            this.gameView.winner(firstPlayer);
        } else {
            secondPlayer.increaseRating();
            firstPlayer.reduceRating();
            this.gameView.winner(secondPlayer);
        }
    }

    void freezeCards(Card first, Card second) {
        first.freezeFaceUp();
        second.freezeFaceUp();
        updateBoard();
    }

    void turnCardsFaceUp(Card first, Card second) {
        first.turnFaceUp();
        second.turnFaceUp();
        updateBoard();
    }

    void turnCardsFaceDown(Card first, Card second) {
        first.turnFaceDown();
        second.turnFaceDown();
        updateBoard();
    }

    Player nextPlayer(Player player, Card firstCard, Card secondCard) {
        if (firstCard.matches(secondCard)) {
            return player;
        }

        if (player.equals(firstPlayer)) {
            return secondPlayer;
        }

        return firstPlayer;
    }

    private void givePlayerSomeTime() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
