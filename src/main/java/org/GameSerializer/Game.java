package org.GameSerializer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.nio.charset.StandardCharsets;


public class Game implements VideoGame, Comparable<Game>, Serializable {
    String title;
    String system;
    int numPlayers;
    String format;
//Constructors
    public Game(){
        this("Choose Title","Unknown", 0,"Unknown");
    }
    public Game(String title){
        this(title, "Unknown",0,"Unknown");
    }
    public Game (String title, int numPlayers){
        this(title,"Unknown",numPlayers,"unknown");
    }
    public Game (String title, String system, int numPlayers, String format){
        this.title = title;
        this.system = system;
        this.numPlayers = numPlayers;
        this.format = format;
    }
    //Mutators
    public void setTitle(String title){this.title = title;}
    public void setSystem(String system){this.system = system;}
    public void setNumPlayers(int numPlayers){this.numPlayers = numPlayers;}
    public void setFormat(String format){this.format = format;}

    //String to write to csv
    public String csvStr() {
        String strplayers = String.valueOf(numPlayers);
        //Returns a string of separated values, the line break is to separate each game in the csv
        return title + "," + system + "," + strplayers + "," + format + "\n";
    }

    //Accessors
    @Override
    public int getPlayers(){
        return numPlayers;
    }
    @Override
    public String getTitle(){
        return title;
    }
    public String getSystem(){
        return system;
    }

    //compareTo returns a neg/pos num or 0 (alphabetically compare titles)
    @Override
    public int compareTo(Game o) {
       return title.compareToIgnoreCase(o.getTitle());
        /*int asciiIn;
        int asciiOut;
        //If all is the same return 0 for match.
        if (title.equalsIgnoreCase(((Game)o).getTitle()) && numPlayers==((Game) o).getPlayers()){

            return 0;
        }
        else {
            if (title.equalsIgnoreCase(((Game) o).getTitle())) {//Compares number of players if title is same
                if (numPlayers > ((Game) o).getPlayers()) {
                    System.out.println("Same title, greater number of players.");
                    return 1;
                } else {
                    System.out.println("Same title, smaller number of players.");
                    return -1;
                }
            }
            else {//This covers if the number of players is the same
                for (int i = 0; i < title.length(); i++) {
                    asciiIn = title.toUpperCase().charAt(i);
                    asciiOut = ((Game) o).getTitle().toUpperCase().charAt(i);
                    if (asciiIn > asciiOut) {
                        System.out.println("Falls before alphabetically");
                        return 1;
                    }
                    else if (asciiIn < asciiOut) {
                        System.out.println("Falls after alphabetically");
                        return -1;
                    }
                    else{
                        continue;
                    }
                }
                return 0;
            }
        }*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return (numPlayers == game.numPlayers) && (((Game) o).getTitle().equals(title));
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, numPlayers);
    }
    public static void serializeToCSV(Game game,String file){
        Path filePath = Paths.get(file);
        try{
            Files.writeString(filePath,game.csvStr(), StandardCharsets.UTF_8);
        }
        catch (IOException e){
            System.out.println("Something went wrong.");
            System.out.println(e.getMessage());
        }
    }
    public static VideoGame deserializeFromCSV(String file) {

        VideoGame gameFromFile = null;
        try {
            String str = Files.readString(Paths.get(file), StandardCharsets.UTF_8);

            String[] line = str.split(",");
            gameFromFile = new Game(line[0].trim(), line[1].trim(), Integer.parseInt(line[2]), line[3].trim());
            /*((Game) gameFromFile).setTitle(line[0]);
            ((Game) gameFromFile).setSystem(line[1]);
            ((Game)gameFromFile).setNumPlayers(Integer.parseInt(line[2]));
            ((Game)gameFromFile).setFormat(line[3]);*/

        } catch (IOException e) {
            System.out.println("Please check the file name.");
        }
        return (Game) gameFromFile;
    }

    public static Set<VideoGame> deserializeSetFromCSV(String file) {
        Set<VideoGame> games = new TreeSet<>();
        try{
            String allLines= Files.readString(Paths.get(file), StandardCharsets.UTF_8);
        String[] lines = allLines.split("\n");
        for(String obj : lines){
            String[] item= obj.split(",");
            if (item[0].equalsIgnoreCase("Title")){
                continue;
            }
            else {
                VideoGame thisgame = new Game(item[0].trim(), item[1].trim(), Integer.parseInt(item[2]), item[3].trim());
                games.add(thisgame);
            }
        }
    }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return games;
    }
    public static void serializeSetToCSV(Set<VideoGame> games, String file) {
        Path filePath = Paths.get(file);

        try{
            Files.writeString(filePath, "Title,System,Players,Format\n");
            for(VideoGame game : games){
                Files.writeString(filePath,game.csvStr(), StandardOpenOption.APPEND);
            }
        }
        catch (IOException e){
            System.out.println("Something went wrong.");
            System.out.println(e.getMessage());
        }

    }

    public static void serializeSetToXML(Set<VideoGame> games, String file){
        try{
            //Path filePath = Paths.get(file);
            XMLEncoder enc;
            enc = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
            enc.writeObject(games);
            enc.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static Set<VideoGame> deserializeFromXML(String file){
        /*Fix me!!
        * For some reason when it deserializes from xml the number of players and the format are not retained.
        * I verified in serializeToXml that the correct values were being passed Find out why they are not retained! */
        Set<VideoGame> games = new TreeSet<>();
        try{
            XMLDecoder dec =new XMLDecoder(new FileInputStream(file));
            games = (Set<VideoGame>) dec.readObject();
            dec.close();

        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        return games;
    }
    public static byte[] toBytes(Set<VideoGame> games){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)){
            oos.writeObject(games);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return baos.toByteArray();
    }
    public static Set<VideoGame> convertFromByte(byte[] bytes){
        Set<VideoGame> games = new TreeSet<>();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            games= (Set<VideoGame>) ois.readObject();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return games;
    }
}
