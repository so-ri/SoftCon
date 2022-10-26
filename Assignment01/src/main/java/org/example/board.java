package org.example;
import org.example.ships.ship;

//! KÖNNEN WIR DAS IN DAS HAUPT-PACKAGE SCHIEBEN?
import org.example.ships.positionX;
import org.example.ships.positionY;

import java.util.Objects;


public class board {
    /*
     * possible types for blockshiptype:
     * EMPTY,
     * CARRIER,
     * BATTLESHIP,
     * CRUISER,
     * SUBMARINE,
     * DESTROYER
     *
     * possible types for blockstate:
     * NOGUESS,
     * HIT,
     * MISSED,
     * SUNK
     *
     * */
    private byte sunkcounter = 0;
    private block[][] blockarray;
    public board(){
        //blockarray iniitierung mit neuen blöcken - OK so? auch mit assertion? die wurde mir vorgeschlagen von IntelliJ
        positionX positionXarray[] = positionX.values(); //helper array of values of enum object to iterate through it
        positionY positionYarray[] = positionY.values();

        for (positionX xpos: positionXarray) { //iterate through enum objects
            for (positionY ypos: positionYarray) {
                assert blockarray != null;
                blockarray[xpos.ordinal()][ypos.ordinal()] = new block();
            }
        }
    }

    public void setGuess(positionX x, positionY y) {
        block block = blockarray[x.ordinal()][y.ordinal()];
        if (IsEmpty(x, y)) {
            block.setState(blockstate.MISSED);
        }
        else {
            block.setState(blockstate.HIT);
            //HERE: tell the ship that it was hit
            if (block.getShipinstance().isDown()){
                block.setState(blockstate.SUNK);
                sunkcounter++;
            }
        }
    }

    //! ShipInstance - is it safe to store it in f.e. 3 blocks at the same time? not needed outside of the board, is it?

    //BLOCK MODIFIERS
    public void setState(positionX x, positionY y, blockstate bs) {
        block block = blockarray[x.ordinal()][y.ordinal()];
        block.setState(bs);
    }
    public void setShipType(positionX x, positionY y, blockshiptype bst) {
        block block = blockarray[x.ordinal()][y.ordinal()];
        block.setShiptype(bst);
    }
    public void setState(positionX x, positionY y, ship s) {
        block block = blockarray[x.ordinal()][y.ordinal()];
        block.setShipinstance(s);
    }

    //BLOCKSTATE CHECKERS
    public boolean GotGuessed(positionX x, positionY y) {
        return !Objects.equals(blockarray[x.ordinal()][y.ordinal()].getState(), blockstate.NOGUESS); //returns true if it has been guessed
    }

    public boolean GotHit(positionX x, positionY y) {
        return !Objects.equals(blockarray[x.ordinal()][y.ordinal()].getState(), blockstate.HIT); //returns true if it has been hit
    }

    public boolean GotMissed(positionX x, positionY y) {
        return !Objects.equals(blockarray[x.ordinal()][y.ordinal()].getState(), blockstate.MISSED); //returns true if it has been missed
    }
    public boolean GotSunk(positionX x, positionY y) {
        return !Objects.equals(blockarray[x.ordinal()][y.ordinal()].getState(), blockstate.SUNK); //returns true if it has been sunk
    }

    //SHIPTYPE CHECKERS
    public boolean IsEmpty(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getShiptype(), blockshiptype.EMPTY); //returns true if == EMPTY, returns false if != EMPTY
    }

    public boolean IsCarrier(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getShiptype(), blockshiptype.CARRIER);
    }

    public boolean IsBattleship(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getShiptype(), blockshiptype.BATTLESHIP);
    }

    public boolean IsCruiser(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getShiptype(), blockshiptype.CRUISER);
    }

    public boolean IsSubmarine(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getShiptype(), blockshiptype.SUBMARINE);
    }

    public boolean IsDestroyer(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getShiptype(), blockshiptype.DESTROYER);
    }

}
