package kalah.Board;

import kalah.Globals;
import kalah.Player.Player;
import java.util.ArrayList;

/**
 * Represents the game board. Holds all of the houses and stores.
 */
public class Board {
    private final ArrayList<Pit> pits;

    public Board(ArrayList<Player> players) {
        this.pits = new ArrayList<>();

        for (Player player : players) {
            for (int i = 0; i < Globals.NUMBER_OF_HOUSES_PER_PLAYER; i++) {
                this.pits.add(new House(Globals.STARTING_SEEDS_IN_PIT, player));
            }
            this.pits.add(new Store(0, player));
        }
    }

    public boolean moveAvailable(Player player) {
        int startingIndex = player.getStartingIndex();
        for (int i = 0; i < Globals.NUMBER_OF_HOUSES_PER_PLAYER; i++) {
            if (this.pits.get(startingIndex+i).getNumberOfSeeds() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean validSelection(int pitNumber, Player player) {
        int boardIndex = this.pitNumberToBoardIndex(pitNumber, player);
        return this.pits.get(boardIndex).numberOfSeeds > 0;
    }

    // Convert the players pit number to a board index
    private int pitNumberToBoardIndex(int pitNumber, Player player) {
        int playerStartIndex = player.getStartingIndex();
        return playerStartIndex + pitNumber - 1; // -1 as index is 0 based while pits are 1 based.
    }

    public void capture(int finalSeedPlaceIndex, int oppositeIndex, Player player){
        int capturedSeeds = this.pits.get(finalSeedPlaceIndex).getAllSeeds() +this.pits.get(oppositeIndex).getAllSeeds();
        int playerStoreIndex = player.getStoreIndex();
        this.pits.get(playerStoreIndex).placeCapturedSeeds(capturedSeeds);
    }

    public MoveOutcome sowSeeds(Player player, int pitNumber) {
        int currentBoardIndex = this.pitNumberToBoardIndex(pitNumber, player);
        int seeds = this.pits.get(currentBoardIndex).getAllSeeds();

        // Sow each seed
        while (seeds > 0) {
            currentBoardIndex = nextIndex(currentBoardIndex);
            if (this.pits.get(currentBoardIndex).placeSeed(player)) {
                seeds--;
            }
        }

        return this.performTurnOutcome(currentBoardIndex, player);
    }

    private MoveOutcome performTurnOutcome(int finalSeedPlaceIndex, Player player) {
        // Check if final seed was in own players store
        if (this.pits.get(finalSeedPlaceIndex).anotherTurn(player)) {
            return MoveOutcome.ANOTHER_TURN;
        }

        // Check if this is a capture
        int oppositePitIndex = this.oppositePitIndex(finalSeedPlaceIndex);
        if (this.pits.get(finalSeedPlaceIndex).getOwner() == player
                && this.pits.get(finalSeedPlaceIndex).getNumberOfSeeds() == 1
                && !this.pits.get(oppositePitIndex).isEmpty()) {
            this.capture(finalSeedPlaceIndex, oppositePitIndex, player);
            return MoveOutcome.CAPTURE;
        }

        return MoveOutcome.TURN_COMPLETE;
    }

    public int oppositePitIndex(int index) {
        return 2 * Globals.NUMBER_OF_HOUSES_PER_PLAYER - index;
    }

    private int nextIndex(int currentIndex){
        return (currentIndex+1) % this.pits.size();
    }

    public int calculateScore(Player player) {
        int score = 0;
        for (Pit pit : this.pits) {
            if (pit.getOwner() == player) {
                score += pit.getNumberOfSeeds();
            }
        }
        return score;
    }

    /* Retrieve the number of seeds in all of a players houses. */
    public ArrayList<Integer> getHouseSeedCounts(Player player) {
        ArrayList<Integer> playerSeeds = new ArrayList<>();
        int startingIndex = player.getStartingIndex();
        for (int i = 0; i < Globals.NUMBER_OF_HOUSES_PER_PLAYER; i++) {
            Pit pit = this.pits.get(startingIndex+i);
            playerSeeds.add(pit.getNumberOfSeeds());
        }
        return playerSeeds;
    }

    public int getStoreSeedCounts(Player player) {
        int storeIndex = player.getStoreIndex();
        Pit store = this.pits.get(storeIndex);
        return store.getNumberOfSeeds();
    }
}
