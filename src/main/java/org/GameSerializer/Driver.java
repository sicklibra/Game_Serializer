package org.GameSerializer;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Set;

import static org.GameSerializer.Game.*;

public class Driver {
    public static void main(String[] args) {
        String file= "gamelog.csv";
        //Create tree set for games to serialize and deserialize to csv.
        Set<Game> gameSet= new TreeSet<>();
        //Create games.
        VideoGame  outGame= new Game("Legend of Zelda", "Nintendo",1, "Cartridge");
        gameSet.add((Game)outGame);
        outGame=new Game("Apex", "Sega",1, "Cartridge");
        gameSet.add((Game)outGame);
        outGame=new Game("Banjo", "Play Station",2, "Disk");
        gameSet.add((Game)outGame);
        outGame= new Game("Legend of Zelda", "Nintendo", 1, "Cartridge");
        gameSet.add((Game)outGame);

        serializeSetToCSV(gameSet, file);
        System.out.println(gameSet.toString());
        Set<Game> gameSetIn = deserializeSetFromCSV(file);
        int i =0;
        for (Game x :gameSetIn){
            System.out.println(x.compareTo((Game)gameSetIn.toArray()[i]));//*****Fix me!****
            i++;
        }
        //serializeToCSV((Game)outGame, file);
        /*Game ingame= deserializeFromCSV(file);
        System.out.println(((Game) outGame).compareTo(ingame));
        System.out.println(((Game) outGame).csvStr());
        System.out.println(ingame.csvStr());
        System.out.println("Do they match?");*/

    }
}