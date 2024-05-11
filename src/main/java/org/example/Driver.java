package org.example;
import  static org.example.Game.serializeToCSV;
import static org.example.Game.deserializeFromCSV;

public class Driver {
    public static void main(String[] args) {
        String file= "gamelog.csv";

        Game outGame= new Game("Legend of Zelda", "Nintendo",1, "Cartridge");
        serializeToCSV(outGame, file);
        Game ingame= deserializeFromCSV(file);
        System.out.println(outGame.equals(ingame));
        System.out.println(outGame.csvStr());
        System.out.println(ingame.csvStr());
        System.out.println("Do they match?");

    }
}