package org.example;

import java.util.Objects;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.List;

public class Game implements VideoGame, Comparable, Serializable {
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
    public void setTitle(String title){
        this.title = title;
    }
    public void setSystem(String system){
        this.system = system;
    }
    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }
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

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return numPlayers == game.numPlayers && Objects.equals(title, game.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, numPlayers);
    }
    public static void serializeToCSV(Game game,String file){
        Path filePath = Paths.get(file);
        try{
            Files.writeString(filePath, game.csvStr());
        }
        catch (IOException e){
            System.out.println("Something went wrong.");
            System.out.println(e.getMessage());
        }
    }
    public static Game deserializeFromCSV(String file){
        Game gameFromFile = new Game();
        try {
            String str = Files.readString(Paths.get(file), StandardCharsets.UTF_8);

            String[] line = str.split(",");
            gameFromFile.setTitle(line[0]);
            gameFromFile.setSystem(line[1]);
            gameFromFile.setNumPlayers(Integer.parseInt(line[2]));
            gameFromFile.setFormat(line[3]);

        }
        catch(IOException e){
            System.out.println("Please check the file name.");
        }
        return gameFromFile;
    }
}
