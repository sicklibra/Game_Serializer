package org.GameSerializer;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Set;

import static org.GameSerializer.Game.*;

public class Driver {
    public static void main(String[] args) {
        String file= "gamelog.csv";
        //Create tree set for games to serialize and deserialize to csv.
        Set<VideoGame> gameSet= new TreeSet<>();
        //Create games.
        VideoGame  outGame= new Game("Legend of Zelda", "Nintendo",1, "Cartridge");
        gameSet.add(outGame);
        outGame=new Game("Apex", "Sega",1, "Cartridge");
        gameSet.add(outGame);
        outGame=new Game("Banjo", "Play Station",2, "Disk");
        gameSet.add(outGame);
        outGame= new Game("Legend of Zelda", "Nintendo", 1, "Cartridge");
        gameSet.add(outGame);

        serializeSetToCSV(gameSet,"gameset.csv");
        System.out.println(gameSet.toString());
        Set<VideoGame> gameSetIn = deserializeSetFromCSV("gameset.csv");
        int i =0;
        for (VideoGame obj :gameSetIn){
            System.out.println(obj.compareTo((Game)gameSet.toArray()[i]));
            i++;
        }
        System.out.println("done");
        //serializeToCSV((Game)outGame, file);
        /*Game ingame= deserializeFromCSV(file);
        System.out.println(((Game) outGame).compareTo(ingame));
        System.out.println(((Game) outGame).csvStr());
        System.out.println(ingame.csvStr());
        System.out.println("Do they match?");*/

    }
}