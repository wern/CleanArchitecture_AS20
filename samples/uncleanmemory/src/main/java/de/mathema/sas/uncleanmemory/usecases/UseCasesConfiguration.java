package de.mathema.sas.uncleanmemory.usecases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {
    @Bean
    public MemoryGame createMemoryGame() {
        return new MemoryGame();
    }
}
