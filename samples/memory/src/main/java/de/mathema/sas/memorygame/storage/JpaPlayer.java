package de.mathema.sas.memorygame.storage;

import de.mathema.sas.memorygame.player.Player;
import de.mathema.sas.memorygame.player.PlayerStorage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class JpaPlayer implements PlayerStorage {

    @Transient
    private PlayerRepository repository;

    @Id
    private Long playerID;

    private String name;
    private int rating;

    // used by JPA to create entity
    private JpaPlayer() {}

    private JpaPlayer(long id, String name, int rating) {
        this.playerID = id;
        this.name = name;
        this.rating = rating;
    }

    public static Player loadPlayer(long id, PlayerRepository repository) {
        JpaPlayer jpaPlayer = repository.findById(id)
                .orElse(new JpaPlayer(id, "Player " + id, Player.INITIAL_RATING));

        jpaPlayer.setRepository(repository);

        Player player = new Player(jpaPlayer.name, jpaPlayer.rating, jpaPlayer);

        return player;
    }

    private void setRepository(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(int rating) {
        this.rating = rating;
        this.repository.save(this);
    }
}
