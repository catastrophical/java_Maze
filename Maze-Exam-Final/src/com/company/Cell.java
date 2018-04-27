package com.company;

/**
 * cell klassen
 */
public class Cell {
    int x;
    int y;
    boolean visited = false;
    boolean rightWall = true, leftWall = true, topWall = true, bottomWall = true;

    // this for at skelne naar x og y er som parametre fra dem i klassen
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }


}



