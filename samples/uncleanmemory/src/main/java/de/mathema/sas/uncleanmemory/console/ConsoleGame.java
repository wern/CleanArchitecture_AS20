package de.mathema.sas.uncleanmemory.console;

import de.mathema.sas.uncleanmemory.entities.Card;
import de.mathema.sas.uncleanmemory.entities.Player;
import de.mathema.sas.uncleanmemory.repository.PlayerRepository;
import de.mathema.sas.uncleanmemory.usecases.GameInterface;
import de.mathema.sas.uncleanmemory.usecases.MemoryGame;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsoleGame {
    private final GameInterface game;
    private PlayerRepository playerRepository;
    private List<Card> cards;
    private List<Player> players;

    public ConsoleGame(MemoryGame game, PlayerRepository playerRepository) {
        this.game = game;
        this.playerRepository = playerRepository;
    }

    public void start() {
        this.cards = this.game.prepareCardDeck();
        this.players = this.game.createPlayers();

        drawBoard();
        startRound(this.players.get(0));
    }

    private void drawBoard() {
        clearScreen();
        showPlayers();
        showCards();
    }

    private void clearScreen() {
        try {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPlayers() {
        this.players.stream().forEach(System.out::println);
    }

    private void showCards() {
        for (int i = 0; i < cards.size(); i++) {
            if (i % 4 == 0) {
                System.out.println();
            }

            System.out.print(i + 1 + ":\t" + cards.get(i) + "\t");
        }
    }

    private void startRound(Player player) {

        System.out.print("\n" + player.getName() + " select card: ");
        Scanner in = new Scanner(System.in);
        int cardPosition = in.nextInt();
        Card firstCard = cards.get(cardPosition - 1);

        System.out.print("\n" + player.getName() + " select card: ");
        in = new Scanner(System.in);
        cardPosition = in.nextInt();
        Card secondCard = cards.get(cardPosition - 1);

        this.game.selectCards(firstCard, secondCard, player);
        drawBoard();
        givePlayerSomeTime();

        if(this.game.finished()) {
            finishGame();
            return;
        } else {
            this.game.hideCards(firstCard, secondCard);
        }

        drawBoard();
        Player nextPlayer = this.game.nextPlayer(player, firstCard, secondCard);
        startRound(nextPlayer);
    }

    private void givePlayerSomeTime() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void finishGame() {
        Player firstPlayer = players.get(0);
        Player secondPlayer = players.get(1);

        if(firstPlayer.outscores(secondPlayer)) {
            firstPlayer.increaseRating();
            secondPlayer.reduceRating();
            System.out.println("\nWinner: " + firstPlayer);

            playerRepository.save(firstPlayer);
            playerRepository.save(secondPlayer);
        } else {
            firstPlayer.reduceRating();
            secondPlayer.increaseRating();
            System.out.println("\nWinner: " + secondPlayer);

            playerRepository.save(firstPlayer);
            playerRepository.save(secondPlayer);
        }
    }
}
