package org.GameSerializer;
import  static org.GameSerializer.Game.serializeToCSV;
import static org.GameSerializer.Game.deserializeFromCSV;

public class Driver {
    public static void main(String[] args) {
        String file= "gamelog.csv";

        VideoGame  outGame= new Game("Legend of Zelda", "Nintendo",1, "Cartridge");
        serializeToCSV((Game)outGame, file);
        Game ingame= deserializeFromCSV(file);
        System.out.println(outGame.equals(ingame));
        System.out.println(((Game) outGame).csvStr());
        System.out.println(ingame.csvStr());
        System.out.println("Do they match?");

    }
}