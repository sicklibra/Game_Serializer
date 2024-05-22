package org.GameSerializer;

import java.util.ArrayList;

public interface VideoGame {
    public String getTitle();
    public int getPlayers();
    public String getSystem();
    public String csvStr();
}
