package kalah.Board;

import kalah.Player.Player;

/**
 * A special pit that seeds are sown to/from.
 */
public class House extends Pit{
    public House(int startingNumberOfSeeds, Player owner) {
        super(startingNumberOfSeeds, owner);
    }

    @Override
    public boolean anotherTurn(Player player) {
        return false;
    }
}
