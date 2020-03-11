package de.mathema.sas.memorygame.player;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {

    @Mock
    PlayerStorage playerStorage;

    Player firstPlayer;
    Player secondPlayer;

    @Before
    public void init() {
        firstPlayer = new Player("name", 1500, playerStorage);
        secondPlayer = new Player("name", 1500, playerStorage);
    }

    @Test
    public void testPlayerScore() {
        assertEquals(false, firstPlayer.outscores(secondPlayer));
        assertEquals(false, secondPlayer.outscores(firstPlayer));

        firstPlayer.scorePoint();

        assertEquals(true, firstPlayer.outscores(secondPlayer));
        assertEquals(false, secondPlayer.outscores(firstPlayer));

        secondPlayer.scorePoint();
        secondPlayer.scorePoint();

        assertEquals(false, firstPlayer.outscores(secondPlayer));
        assertEquals(true, secondPlayer.outscores(firstPlayer));
    }

    @Test
    public void testIncreaseRating() {
        firstPlayer.increaseRating();

        verify(playerStorage).save(1501);
    }

    @Test
    public void testReduceRating() {
        firstPlayer.reduceRating();

        verify(playerStorage).save(1499);
    }
}
