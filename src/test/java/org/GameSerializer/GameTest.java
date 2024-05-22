package org.GameSerializer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @BeforeEach
    void setUp() {
        VideoGame game1 = new Game("zelda", "nintendo",2,"cart");
        VideoGame game2 = new Game("apex   ", "all",60,"digital");
        //String strTest1 = game1.csvStr();
        //String strTest2 = game2.csvStr();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void csvStr() {
    }

    @Test
    void testCsvStr() {
        String strTest1= game1.csvStr();
    }

    @Test
    void compareTo() {
    }

    @Test
    void deserializeSetFromCSV() {
    }
}