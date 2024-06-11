package org.GameSerializer;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.StandardOpenOption;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;
import java.util.Set;
import static org.GameSerializer.Game.*;

public class Driver {
    public static byte[] convertObjectToBytes(Set<Object> objects) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(objects);
            return boas.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }
    public static void serializeToBin(String file, Set<Object> outSet){
        byte[] outByte= convertObjectToBytes(outSet);
        try{
            Files.write(Paths.get(file),outByte);
    }
        catch(Exception e){
        System.out.println(e.getMessage());
        }
    }
    public static Set<Objects> deserializeObjSetFromBin(String file){
        Set<Objects> outset = new TreeSet();
        return outset;
    }
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
        serializeSetToXML(gameSet, "games.xml");
        gameSetIn=deserializeFromXML("games.xml");
        for (VideoGame obj :gameSetIn){
            System.out.println(obj.csvStr());
        }
        serializeToBin("games.bin",gameSet);
        System.out.println("done");
        //serializeToCSV((Game)outGame, file);
        /*Game ingame= deserializeFromCSV(file);
        System.out.println(((Game) outGame).compareTo(ingame));
        System.out.println(((Game) outGame).csvStr());
        System.out.println(ingame.csvStr());
        System.out.println("Do they match?");*/

    }
}