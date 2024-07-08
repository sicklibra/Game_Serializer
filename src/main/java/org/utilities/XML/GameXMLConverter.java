package org.utilities.XML;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.GameSerializer.Game;
import org.GameSerializer.VideoGame;

public class GameXMLConverter implements Converter{
    /*check argument <code>clazz<code> against <code>Game<code> to ensure that object
     is correct type.
     @param clazz
     @pre ensure clazz is a valid Game
     @post boolean returned comparing clazz to Game.class
     */
    public boolean canConvert(Class clazz) {return Game.class.isAssignableFrom(clazz);}

    /**
     * writes argument <code>value</code>
     * @param value <code>Game</code> to write to file
     * @param writer stream to write through
     * @param context <code>MarshallingContext</code> store generic data
     * @pre <code>value</code> is a valid <code>Game</code>,
     * <code>writer</code> is a valid <code>HierarchicalStreamWriter</code>
     * <code>context</code> is valid <code>MarshallingContext</code>
     * @post <code>value</code> is written to specified XML file via writer
     */
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        Game game = (Game)value;

        writer.startNode("Title");
        writer.setValue(game.getTitle());
        writer.endNode();

        writer.startNode("System");
        writer.setValue(game.getSystem());
        writer.endNode();

        writer.startNode("Players");
        writer.setValue(String.valueOf(game.getPlayers()));
        writer.endNode();

        writer.startNode("Format");
        writer.setValue(game.getFormat());
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

        VideoGame game = new Game();
        reader.moveDown();
        game.setTitle(reader.getValue());
        reader.moveUp();

        reader.moveDown();
        game.setSystem(reader.getValue());
        reader.moveUp();

        reader.moveDown();
        game.setPlayers(Integer.parseInt(reader.getValue()));
        reader.moveUp();

        reader.moveDown();
        game.setFormat(reader.getValue());
        reader.moveUp();

        return game;
    }

}
