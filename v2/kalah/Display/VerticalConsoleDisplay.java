package kalah.Display;

import com.qualitascorpus.testsupport.IO;
import kalah.Board.Board;
import kalah.Globals;
import kalah.Player.Player;

import java.util.ArrayList;

public class VerticalConsoleDisplay extends ConsoleDisplay {
    public VerticalConsoleDisplay(IO outputDevice) {
        super(outputDevice);
    }

    @Override
    public void displayBoard(final Board board, final ArrayList<Player> players){
        // Get players and house seeds
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        ArrayList<Integer> player1HouseSeeds = board.getHouseSeedCounts(player1);
        ArrayList<Integer> player2HouseSeeds = board.getHouseSeedCounts(player2);

        // Print first store
        this.printStore(board, player2, true);

        // Print the players houses
        for(int leftHouseNumber = 1, rightHouseNumber = player2HouseSeeds.size();
            leftHouseNumber <= player1HouseSeeds.size(); leftHouseNumber++, rightHouseNumber--) {

            int leftPitIndex = leftHouseNumber - 1;
            int rightPitIndex = rightHouseNumber - 1;

            int leftNumberOfSeeds = player1HouseSeeds.get(leftPitIndex);
            int rightNumberOfSeeds = player2HouseSeeds.get(rightPitIndex);

            String boardRow = this.buildBoardRow(leftHouseNumber, leftNumberOfSeeds, rightHouseNumber, rightNumberOfSeeds);
            this.outputDevice.println(boardRow);
        }

        // Print second store
        this.printStore(board, player1, false);
    }

    private void printStore(Board board, Player storeOwner, boolean isTopStore) {
        final String outerBorder = "+---------------+";
        final String innerBorder = "+-------+-------+";

        // Print empty pit and player store.
        if (isTopStore) {
            this.outputDevice.println(outerBorder);
            this.outputDevice.print("|       |");
            this.outputDevice.print(this.buildPlayerNameString(storeOwner));
            this.outputDevice.println(this.buildStoreString(board, storeOwner));
            this.outputDevice.println(innerBorder);
        } else {
            this.outputDevice.println(innerBorder);
            this.outputDevice.print("|");
            this.outputDevice.print(this.buildPlayerNameString(storeOwner));
            this.outputDevice.print(this.buildStoreString(board, storeOwner));
            this.outputDevice.println("       |");
            this.outputDevice.println(outerBorder);
        }
    }

    private String buildBoardRow (int leftHouseNumber, int leftSeedCount, int rightHouseNumber, int rightSeedCount) {
        String row = "|";
        row += this.buildHouseString(leftHouseNumber, leftSeedCount);
        row += "|";
        row += this.buildHouseString(rightHouseNumber, rightSeedCount);
        row += "|";
        return row;
    }
}
