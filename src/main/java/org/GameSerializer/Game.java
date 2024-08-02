package org.GameSerializer;
import org.utilities.binSerializer;

import org.utilities.XMLSerializerInterface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static org.utilities.binSerializer.*;


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
    public Game(String title, int numPlayers){
        this(title,"Unknown",numPlayers,"unknown");
    }
    public Game(String title, String system, int numPlayers, String format){
        this.title = title;
        this.system = system;
        this.numPlayers = numPlayers;
        this.format = format;
    }
    //Mutators
    public void setTitle(String title){this.title = title;}
    public void setSystem(String system){this.system = system;}
    public void setPlayers(int numPlayers){this.numPlayers = numPlayers;}
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
    public String getFormat(){return format;}

    //compareTo returns a neg/pos num or 0 (alphabetically compare titles)
    @Override
    public int compareTo(Game o) {
       return title.compareToIgnoreCase(o.getTitle());
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
        } catch (IOException e) {
            System.out.println("Please check the file name.");
        }
        return gameFromFile;
    }

    public static Set<VideoGame> deserializeSetFromCSV(String file) {
        Set<VideoGame> games = new TreeSet<>();
        try{
            //read all lines
            String allLines= Files.readString(Paths.get(file), StandardCharsets.UTF_8);
        String[] lines = allLines.split("\n");
        // if not item then do it. make title line in csv a constant.
            for(String obj : lines){
                String[] item= obj.split(",");
                if (!(item[0].equalsIgnoreCase("Title"))){
                    VideoGame mygame = new Game(item[0].trim(), item[1].trim(), Integer.parseInt(item[2]), item[3].trim());
                    games.add(mygame);
                }
            }
        }
        catch (IOException e){
            // specify what function error message is in.
            System.out.println(e.getMessage());
        }
        return games;
    }
    public static void serializeSetToCSV(Set<VideoGame> games, String file)throws IOException {
        Path filePath = Paths.get(file);
        Files.writeString(filePath, "Title,System,Players,Format\n");
        for(VideoGame game : games){
            Files.writeString(filePath,game.csvStr(), StandardOpenOption.APPEND);
        }
    }

    public static void serializeSetToXML(Set<VideoGame> games, String file)throws IOException{
        //impliment practices from the interface into this section.
        //use for loop to make set into array "walking" .size
        for (VideoGame game : games){
            game.serializeXMLObject();
        }
//            XMLEncoder enc;
//            enc = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
//            enc.writeObject(games);
//            enc.close();
    }


    //This will be what i fix in the marshalling setup using the xstream system
    public static Set<VideoGame> deserializeFromXML(String file)throws IOException{
        Set<VideoGame> games = new TreeSet<>();
        VideoGame gameFromFile = null;
        gameFromFile=readXMLObject(file,canConvert(readXMLObject()));
//        //Data lost in this function re-working with xstream
//        Set<VideoGame> games = new TreeSet<>();
//        XMLDecoder dec =new XMLDecoder(new FileInputStream(file));
//        games = (Set<VideoGame>) dec.readObject();
//        dec.close();
//        return games;
    }

    public static void setToFile(Set<VideoGame> games, String file){
        try {
            serializeObjectToFile(games, file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void deserializeFromBinFile(String file)throws Exception{
        Set<VideoGame> games = null;
        Set<Object> undefSet = null;
        try {
            undefSet = deserializeSetFromBytes(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Object o : undefSet){
            VideoGame game = (VideoGame) o;
            games.add(game);
        }

    }


}
