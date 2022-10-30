package org.example;
import org.example.ships.ship;

import org.example.ships.positionX;
import org.example.ships.positionY;

import java.util.Objects;


public class board {
    /*
     * possible types for blockshiptype:
     * EMPTY,
     * CARRIER,
     * *BATTLESHIP,
     * *CRUISER,
     * *SUBMARINE,
     * *DESTROYER
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
        positionX[] positionXarray = positionX.values(); //helper array of values of enum object to iterate through it
        positionY[] positionYarray = positionY.values();

        for (positionX xpos: positionXarray) { //iterate through enum objects
            for (positionY ypos: positionYarray) {
                assert blockarray != null;
                blockarray[xpos.ordinal()][ypos.ordinal()] = new block();
            }
        }
    }

    public boolean IsGameOver() {
        return sunkcounter >= 10;
    }

    public void createShip(positionX x, positionY y, positionX x2, positionY y2) {
        ship ship = new ship(x,y,x2,y2);
        blockshiptype shiptype = ship.getTypeAsEnumerate(); //cache the shiptype
        for (Pair<positionX, positionY> pair: ship.getCoordinates()) { // go through all the shipinstance coordinates //requires java tuple module
            block newshipblock = blockarray[pair.getValue(0).ordinal()][pair.getValue(1).ordinal()]; //find the block with the coordinates
            newshipblock.setShiptype(shiptype);
            newshipblock.setShipinstance(ship);
        }
    }

    public void setGuess(positionX x, positionY y) {
        block block = blockarray[x.ordinal()][y.ordinal()];
        if (IsEmpty(x, y)) {
            block.setState(blockstate.MISSED);
        }
        else {
            block.setState(blockstate.HIT);
            block.setShiptoHit(x,y);
            if (block.getShipinstance().isDown()){
                for (Pair<positionX, positionY> pair: block.getShipinstance().getCoordinates()) { // go through all the shipinstance coordinates //requires java tuple module
                    block sunkblock = blockarray[pair.getValue(0).ordinal()][pair.getValue(1).ordinal()]; //find the block with the coordinates
                    sunkblock.setState(blockstate.SUNK); //set it to sunk
                }
                sunkcounter++;
            }
        }
    }


    //BLOCK MODIFIERS
    public void setState(positionX x, positionY y, blockstate bs) {
        block block = blockarray[x.ordinal()][y.ordinal()];
        block.setState(bs);
    }
    public void setShipType(positionX x, positionY y, blockshiptype bst) {
        block block = blockarray[x.ordinal()][y.ordinal()];
        block.setShiptype(bst);
    }
    //! ShipInstance - is it safe to store it in f.e. 3 blocks at the same time?
    public void setShipInstance(positionX x, positionY y, ship s) {
        block block = blockarray[x.ordinal()][y.ordinal()];
        //ONLY CHANGE SHIPINSTANCE IF IT WAS NOT SET YET - OK wenn ich mache = NULL?
        block.setShipinstance(s);
    }

    //BLOCKSTATE CHECKERS
    public boolean GotGuessed(positionX x, positionY y) {
        return !Objects.equals(blockarray[x.ordinal()][y.ordinal()].getState(), blockstate.NOGUESS); //returns true if it has been guessed
    }

    public boolean GotHit(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getState(), blockstate.HIT); //returns true if it has been hit
    }

    public boolean GotMissed(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getState(), blockstate.MISSED); //returns true if it has been missed
    }
    public boolean GotSunk(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getState(), blockstate.SUNK); //returns true if it has been sunk
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

    public boolean IsPatrol(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getShiptype(), blockshiptype.PATROL);
    }

    public boolean IsSubmarine(positionX x, positionY y) {
        return Objects.equals(blockarray[x.ordinal()][y.ordinal()].getShiptype(), blockshiptype.SUBMARINE);
    }


}
