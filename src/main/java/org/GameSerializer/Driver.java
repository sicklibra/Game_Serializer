package org.GameSerializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import org.utilities.XML.XMLSerializerInterface;

import static org.GameSerializer.Game.*;

/*Get serializers and testing working*/

public class Driver {
    //serializes to binary file using byte array
    public static void serializeToBin(String file, byte[] bytes){
        try{
            Path fileOut= Paths.get(file);
            Files.write(fileOut, bytes);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Deserializes byte array from previous file
    public static byte[] deserializeObjSetFromBin(String file){
        byte[] outByte=new byte[0];
        try {
            Path inFile= Paths.get(file);
            outByte=Files.readAllBytes(inFile);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return outByte;
    }

    public static void main(String[] args) {
        //Initialize filenames
        String csvFile= "gamelog.csv";
        String xmlFile= "gamelog.xml";
        String datFile= "gamelog.dat";
        Set<VideoGame> gameSetIn = null;
        //Create tree set for games to serialize and deserialize to csv.
        Set<VideoGame> gameSet= new TreeSet<>();

        //Create games.
        VideoGame  outGame= new Game("Legend of Zelda", "Nintendo",1, "Cartridge");
        gameSet.add(outGame);
        outGame=new Game("Apex", "Sega",1, "Cartridge");
        gameSet.add(outGame);
        outGame=new Game("Banjo", "Play Station",2, "Disk");
        gameSet.add(outGame);

        //Serialization processes.
        try {
            serializeSetToCSV(gameSet,csvFile);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            gameSetIn = deserializeSetFromCSV(csvFile);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Loop to Print compareTo results in deserialized gameSetIn
        /*int i=0;
        for (VideoGame obj :gameSetIn){
            System.out.println(obj.compareTo((Game)gameSet.toArray()[i]));
            i++;
        }*/

        //Serializing to XML current work to marshall accordingly
        try {
            serializeSetToXML(gameSet, xmlFile);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            gameSetIn=deserializeFromXML(xmlFile);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
//        for (VideoGame obj :gameSetIn){
//            System.out.println(obj.csvStr());
//        }


        // Serialize to binary using byte array
        try {
            byte[] bytearr = toBytes(gameSet);
            serializeToBin(datFile, bytearr);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] inBytes=deserializeObjSetFromBin(datFile);
        try {
            gameSetIn= convertFromByte(inBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // used for debugging purpouses
        System.out.println("done");
    }
}