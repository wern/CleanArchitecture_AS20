package de.mathema.sas.memorygame;

import de.mathema.sas.memorygame.consolegame.ConsoleGameConfiguration;
import de.mathema.sas.memorygame.player.Player;
import de.mathema.sas.memorygame.rules.Game;
import de.mathema.sas.memorygame.storage.JpaPlayer;
import de.mathema.sas.memorygame.storage.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

	@Autowired
	ConsoleGameConfiguration consoleGameConfiguration;

	@Autowired
	PlayerRepository playerRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Player firstPlayer = JpaPlayer.loadPlayer(1, playerRepository);
		Player secondPlayer = JpaPlayer.loadPlayer(2, playerRepository);

		Game game = consoleGameConfiguration.createMemoryGame(firstPlayer, secondPlayer);
		game.start();
	}
}
