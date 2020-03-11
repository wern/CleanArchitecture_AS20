package de.mathema.sas.uncleanmemory.console;

import de.mathema.sas.uncleanmemory.repository.PlayerRepository;
import de.mathema.sas.uncleanmemory.usecases.MemoryGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsoleConfiguration {

    @Autowired
    MemoryGame game;

    @Autowired
    PlayerRepository repository;

    @Bean
    public ConsoleGame createConsoleView() {
        return new ConsoleGame(game, repository);
    }
}
