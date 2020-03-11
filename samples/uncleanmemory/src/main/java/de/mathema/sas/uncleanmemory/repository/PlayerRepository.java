package de.mathema.sas.uncleanmemory.repository;

import de.mathema.sas.uncleanmemory.entities.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
