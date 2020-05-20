package kalah.Board;
import kalah.Player.Player;

/**
 * An abstract pit to hold seeds
 */
abstract public class Pit {
    protected int numberOfSeeds;
    protected final Player owner;

    public Pit(int startingNumberOfSeeds, Player owner) {
        this.numberOfSeeds = startingNumberOfSeeds;
        this.owner = owner;
    }

    public int getOwnerName(){
        return owner.getName();
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getNumberOfSeeds(){
        return this.numberOfSeeds;
    }

    // Empty a house and return all seeds that were inside.
    public int getAllSeeds() {
        int seedsToReturn = this.numberOfSeeds;
        this.numberOfSeeds = 0;
        return seedsToReturn;
    }

    public boolean isEmpty() {
        return this.numberOfSeeds == 0;
    }

    public boolean placeSeed(Player player){
        this.numberOfSeeds++;
        return true;
    }

    public void placeCapturedSeeds(int numberOfSeedsCaptured){
        this.numberOfSeeds += numberOfSeedsCaptured;
    }

    abstract public boolean anotherTurn(Player player);
}
