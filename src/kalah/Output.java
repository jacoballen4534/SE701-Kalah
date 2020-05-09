package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Board.Board;

public class Output {
    private final IO outputDevice;

    public Output(IO outputDevice) {
        this.outputDevice = outputDevice;
    }

    public void displayMessage(String message) {
        this.outputDevice.println(message);
    }

    public void displayBoard(Board board){
        // Print top border
        this.outputDevice.println("+----+-------+-------+-------+-------+-------+-------+----+");

        // Print first line
        int firstRowPitIndex = board.getFinalPitIndex();
        this.outputDevice.print("| P" + board.getPitOwnerName(firstRowPitIndex--) + " |");
        for(int displayPitNumber = Globals.NUMBER_OF_HOUSES_PER_PLAYER; displayPitNumber > 0; displayPitNumber--) {
            this.outputDevice.print(" " + displayPitNumber + "[" + this.padNumbers(board.getSeedCount(firstRowPitIndex--)) + "] |");
        }
        this.outputDevice.println(" " + this.padNumbers(board.getSeedCount(firstRowPitIndex)) + " |");

        // print center separator
        this.outputDevice.println("|    |-------+-------+-------+-------+-------+-------|    |");

        // Print second line
        int secondRowPitIndex = 0;
        this.outputDevice.print("| " + this.padNumbers(board.getSeedCount(board.getFinalPitIndex())) + " |");
        for(int displayPitNumber = 1; displayPitNumber <= Globals.NUMBER_OF_HOUSES_PER_PLAYER; displayPitNumber++) {
            this.outputDevice.print(" " + displayPitNumber + "[" + this.padNumbers(board.getSeedCount(secondRowPitIndex++)) + "] |");
        }
        this.outputDevice.println(" P" + board.getPitOwnerName(secondRowPitIndex) + " |");

        // Print bottom border
        this.outputDevice.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    private String padNumbers(int numberToPad) {
        String padding = "";
        if (numberToPad < 10) {
            padding = " ";
        }
        return padding + numberToPad;
    }
}
