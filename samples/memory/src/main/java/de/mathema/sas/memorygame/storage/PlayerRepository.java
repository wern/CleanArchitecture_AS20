package de.mathema.sas.memorygame.storage;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<JpaPlayer, Long> {
}
