package kalah.Player;

import kalah.Globals;

abstract public class Player {
    protected final int id;

    public Player(int id) {
        this.id = id;
    }

    public int getName() {
        return this.id+1;
    }

    // Returns the index of the players first pit
    public int getStartingIndex() {
        int numberOfPits = Globals.NUMBER_OF_HOUSES_PER_PLAYER + 1;
        return (this.id * numberOfPits);
    }

    // Returns the index of the players store
    public int getStoreIndex() {
        return this.getStartingIndex() + Globals.NUMBER_OF_HOUSES_PER_PLAYER;
    }

    public abstract int selectPit();
}
