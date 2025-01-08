package org.utilities;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.TreeSet;
import org.GameSerializer.VideoGame;

public class BinSerializer {

    public static void serializeObjectToFile(Object obj, String file)throws IOException{
        byte[] outBytes = null;
        try{
            outBytes = toBytes(obj);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        Path fileout=Paths.get(file);
        Files.write(fileout,outBytes, StandardOpenOption.APPEND);

    }
    public static void serializeObjectToFile(Set<VideoGame> games, String file) throws IOException {
        byte[] outBytes=null;
        try{
            outBytes= toBytes(games);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        Path fileOut=Paths.get(file);
        Files.write(fileOut,outBytes);
    }
    private static byte[] toBytes(Object O) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(O);
        return baos.toByteArray();
    }
    private static byte[] toBytes(Set<Object> objectSet) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos= new ObjectOutputStream(baos);
        oos.writeObject(objectSet);
        return baos.toByteArray();
    }

    public static Set<Object> deserializeSetFromBytes(String file)throws IOException{
        Set<Object> objects = null;
        byte[] inBytes = null;
        try {
            inBytes=getBytes(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        objects = convertSetFromByte(inBytes);

        return objects;
    }
    private static byte[] getBytes(String file)throws IOException{
        byte[] bytes = null;
        Path path = Paths.get(file);
        bytes = Files.readAllBytes(path);
        return bytes;
    }

    private static Set<Object> convertSetFromByte(byte[] bytes)throws IOException{
        //Fix me like before
        Set<Object> objects = new TreeSet<>();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return objects;
    }
}

