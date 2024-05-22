package org.GameSerializer;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.Serializable;

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
            String Checkstring = Files.readString(filePath, StandardCharsets.UTF_8);
        String[] lines = Checkstring.split("\n");
        for (String line : lines) {
            String[] split = line.split(",");
            if(split[0].trim().equals(game.title)) {
                return;
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        try{
            Files.writeString(filePath,game.csvStr(), StandardOpenOption.APPEND);
        }
        catch (IOException e){
            System.out.println("Something went wrong.");
            System.out.println(e.getMessage());
        }
    }
    public static Game deserializeFromCSV(String file) {

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

    public static Set<Game> deserializeSetFromCSV(String file) {
        Set<Game> games = new TreeSet<>();
        try{
            String allLines= Files.readString(Paths.get(file), StandardCharsets.UTF_8);
        String[] lines = allLines.split("\n");
        for(String x : lines){
            String[] item= x.split(",");
            VideoGame thisgame= new Game(item[0].trim(),item[1].trim(),Integer.parseInt(item[2]),item[3].trim());
            games.add((Game)thisgame);
        }
    }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return games;
    }
    public static void serializeSetToCSV(Set<Game> games, String file) {
        for(Game game : games){
            serializeToCSV(game,file);
        }

    }
}
