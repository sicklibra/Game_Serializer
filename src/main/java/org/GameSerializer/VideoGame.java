package org.GameSerializer;

import java.util.ArrayList;

public interface VideoGame {
    public String getTitle();
    public int getPlayers();
    public String getSystem();
    public String csvStr();
    public int compareTo(Game o);
    public void setTitle (String title);
    public void setPlayers (int players);
    public void setSystem (String system);
    public void setFormat (String format);


}
