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
        // Print first store
        int firstStoreIndex = board.getFinalPitIndex();
        this.printStore(board.getPitOwnerName(firstStoreIndex), board.getSeedCount(firstStoreIndex), true);

        // Print the players houses
        for(int leftHouseNumber = 1, rightHouseNumber = Globals.NUMBER_OF_HOUSES_PER_PLAYER;
            leftHouseNumber <= Globals.NUMBER_OF_HOUSES_PER_PLAYER; leftHouseNumber++, rightHouseNumber--) {

            int leftPitIndex = leftHouseNumber-1;
            int rightPitIndex = board.oppositePitIndex(leftPitIndex);

            int leftSeedNumber = board.getSeedCount(leftPitIndex);
            int rightSeedNumber = board.getSeedCount(rightPitIndex);

            String boardRow = this.buildBoardRow(leftHouseNumber, leftSeedNumber, rightHouseNumber, rightSeedNumber);
            this.outputDevice.println(boardRow);
        }

        // Print second store
        int secondStoreIndex = Globals.NUMBER_OF_HOUSES_PER_PLAYER;
        this.printStore(board.getPitOwnerName(secondStoreIndex), board.getSeedCount(secondStoreIndex), false);
    }

    private void printStore (int playerName, int score, boolean isTopStore) {
        final String emptyContainer = "|       ";
        final String topBorder = isTopStore ? "+---------------+" : "+-------+-------+";
        final String bottomBorder = isTopStore ? "+-------+-------+" : "+---------------+";

        // Print the top border of the store
        this.outputDevice.println(topBorder);

        // Print empty pit and player store.
        if (isTopStore) {
            this.outputDevice.println(emptyContainer + "| P" + playerName + " " + this.padNumbers(score) + " |");
        } else {
            this.outputDevice.println("| P" + playerName + " " + this.padNumbers(score) + " " + emptyContainer + "|");
        }

        // Print the bottom border of the store.
        this.outputDevice.println(bottomBorder);
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
