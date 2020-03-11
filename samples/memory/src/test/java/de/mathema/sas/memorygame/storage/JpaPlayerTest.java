package de.mathema.sas.memorygame.storage;

import de.mathema.sas.memorygame.consolegame.ConsoleGameConfiguration;
import de.mathema.sas.memorygame.player.Player;
import de.mathema.sas.memorygame.rules.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaPlayerTest {

    private static final int PLAYER_ID = 99;

    // use mock game so the spring app would start
    @TestConfiguration
    static class ConsoleGameTestCondifuration {
        ConsoleGameConfiguration mockConfig = Mockito.mock(ConsoleGameConfiguration.class);

        @Bean
        public ConsoleGameConfiguration createConsoleGameConfiguration() {
            doReturn(Mockito.mock(Game.class)).when(mockConfig).createMemoryGame(any(), any());
            return mockConfig;
        }
    }

    @Autowired
    PlayerRepository repository;

    @Test
    public void testCreateNewPlayer() {
        Player player = JpaPlayer.loadPlayer(PLAYER_ID, repository);

        Player expectedPlayer = new Player("Player " + PLAYER_ID, Player.INITIAL_RATING, null);

        assertEquals(expectedPlayer, player);
    }

    @Test
    public void testSaveNewPlayer() {
        Player player = JpaPlayer.loadPlayer(PLAYER_ID, repository);
        player.increaseRating();

        Player savedPlayer = JpaPlayer.loadPlayer(PLAYER_ID, repository);
        assertEquals(player, savedPlayer);

        Player playerWithIncreasedRating = new Player("Player " + PLAYER_ID, Player.INITIAL_RATING + 1, null);
        assertEquals(playerWithIncreasedRating, savedPlayer);
    }

    @Test
    public void testUpdateExistingPlayer() {
        Player newPlayer = JpaPlayer.loadPlayer(PLAYER_ID, repository);
        newPlayer.increaseRating();

        Player existingPlayer = JpaPlayer.loadPlayer(PLAYER_ID, repository);
        existingPlayer.increaseRating();

        Player updatedPlayer = JpaPlayer.loadPlayer(PLAYER_ID, repository);

        Player expectedPlayer = new Player("Player " + PLAYER_ID, Player.INITIAL_RATING + 2, null);
        assertEquals(expectedPlayer, updatedPlayer);
    }
}
