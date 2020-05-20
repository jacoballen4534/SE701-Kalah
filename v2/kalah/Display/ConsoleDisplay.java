package kalah.Display;

import com.qualitascorpus.testsupport.IO;
import kalah.Board.Board;
import kalah.Globals;
import kalah.Player.Player;

import java.util.ArrayList;

/**
* An output adapter implementation to interface with a console to display the game data.
*/

public class ConsoleDisplay implements OutputAdapter{
    protected final IO outputDevice;

    public ConsoleDisplay(IO outputDevice) {
        this.outputDevice = outputDevice;
    }

    public void displayMessage(String message) {
        this.outputDevice.println(message);
    }

    public void displayBoard(final Board board, final ArrayList<Player> players){
        // Get players and house seeds
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        ArrayList<Integer> player1HouseSeeds = board.getHouseSeedCounts(player1);
        ArrayList<Integer> player2HouseSeeds = board.getHouseSeedCounts(player2);

        // Create border string
        StringBuilder border = new StringBuilder();
        for (int i = 0; i < Globals.NUMBER_OF_HOUSES_PER_PLAYER; i++){
            border.append("-------+");
        }
        border.setLength(border.length() - 1);

        // Print top border
        this.outputDevice.println( "+----+" + border.toString() + "+----+");

        // Print first line
        this.outputDevice.print("|" + this.buildPlayerNameString(player2) + " |");

        for(int pitNumber = player2HouseSeeds.size(); pitNumber > 0; pitNumber--) {
            int pitIndex = pitNumber - 1;
            int numberOfSeeds = player2HouseSeeds.get(pitIndex);
            this.outputDevice.print(this.buildHouseString(pitNumber, numberOfSeeds) + "|");
        }

        this.outputDevice.println(this.buildStoreString(board, player1));

        // print center separator
        this.outputDevice.println( "|    |" + border.toString() + "|    |");

        // Print second line
        this.outputDevice.print("|" + this.buildStoreString(board, player2));

        for(int pitNumber = 1; pitNumber <= player1HouseSeeds.size(); pitNumber++) {
            int pitIndex = pitNumber - 1;
            int numberOfSeeds = player1HouseSeeds.get(pitIndex);
            this.outputDevice.print(this.buildHouseString(pitNumber, numberOfSeeds) + "|");
        }

        this.outputDevice.println(this.buildPlayerNameString(player1) + " |");

        // Print bottom border
        this.outputDevice.println( "+----+" + border.toString() + "+----+");
    }

    protected String padNumbers(int numberToPad) {
        String padding = "";
        if (numberToPad < 10) {
            padding = " ";
        }
        return padding + numberToPad;
    }

    protected String buildHouseString (int houseNumber, int seedCount) {
        return " " + houseNumber + "[" + this.padNumbers(seedCount) + "] ";
    }

    protected String buildPlayerNameString(Player player) {
        return " P" + player.getName();
    }

    protected String buildStoreString(Board board, Player storeOwner){
        int seedsInStore = board.getStoreSeedCounts(storeOwner);
        return " " + this.padNumbers(seedsInStore) + " |";
    }
}
