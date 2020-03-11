package de.mathema.sas.uncleanmemory;

import de.mathema.sas.uncleanmemory.console.ConsoleGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemoryApplication implements ApplicationRunner {

	@Autowired
    ConsoleGame consoleGame;

	public static void main(String[] args) {
		SpringApplication.run(MemoryApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		consoleGame.start();
	}
}
