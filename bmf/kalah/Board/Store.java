package kalah.Board;

import kalah.Player.Player;

/**
 * A special pit that all captured seeds go to
 */
public class Store extends Pit{
    public Store(int startingNumberOfSeeds, Player owner) {
        super(startingNumberOfSeeds, owner);
    }

    @Override
    // Prevent placing a seed in the opponents store.
    public boolean placeSeed(Player player) {
        if (this.owner == player) {
            return super.placeSeed(player);
        }
        return false;
    }

    @Override
    public boolean anotherTurn(Player player) {
        return this.owner == player;
    }
}
