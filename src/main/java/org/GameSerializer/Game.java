package org.GameSerializer;

import java.util.Objects;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.io.Serializable;

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
        int asciiIn;
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
        }

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
        VideoGame gameFromFile = new Game();
        try {
            String str = Files.readString(Paths.get(file), StandardCharsets.UTF_8);

            String[] line = str.split(",");
            ((Game) gameFromFile).setTitle(line[0]);
            ((Game) gameFromFile).setSystem(line[1]);
            ((Game)gameFromFile).setNumPlayers(Integer.parseInt(line[2]));
            ((Game)gameFromFile).setFormat(line[3]);

        }
        catch(IOException e){
            System.out.println("Please check the file name.");
        }
        return (Game)gameFromFile;
    }
}