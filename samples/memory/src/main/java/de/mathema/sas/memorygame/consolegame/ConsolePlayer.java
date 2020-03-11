package de.mathema.sas.memorygame.consolegame;

import de.mathema.sas.memorygame.player.PlayerView;

public class ConsolePlayer implements PlayerView {

    @Override
    public void showInfo(String name, int rating, int score) {
        System.out.println(name + " (" + rating +  "): " + score);
    }

    @Override
    public void showName(String name) {
        System.out.print(name);
    }
}
