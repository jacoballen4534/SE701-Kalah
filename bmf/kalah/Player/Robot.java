package kalah.Player;

import kalah.Board.Board;
import kalah.Board.MoveOutcome;
import kalah.Display.OutputAdapter;
import kalah.Globals;

import java.util.ArrayList;

public class Robot extends Player{

    private OutputAdapter output;
    private Board testingBoard;
    private Board boardToCopyFrom;

    public Robot(int id, OutputAdapter output) {
        super(id);
        this.output = output;
    }

    public void setupTestingBoard(ArrayList<Player> players, Board realBoard) {
        this.testingBoard = new Board(players);
        this.boardToCopyFrom = realBoard;
    }

    @Override
    public int selectPit() {
        for (MoveOutcome desiredOutcome : MoveOutcome.values()) {
            for (int boardIndex = 1; boardIndex <= Globals.NUMBER_OF_HOUSES_PER_PLAYER; boardIndex++) {
                this.testingBoard.copySeedArrangement(this.boardToCopyFrom);
                if (!this.testingBoard.validSelection(boardIndex, this)){
                    continue;
                }
                MoveOutcome moveOutcome = this.testingBoard.sowSeeds(this, boardIndex);
                if (moveOutcome == desiredOutcome) {
                    this.output.displayMessage(this.createMoveExplanationString(boardIndex, moveOutcome));
                    return boardIndex;
                }
            }
        }
        // This should never get here as this should not be called if there are no moves available.
        return -1;
    }

    private String createMoveExplanationString(int houseNumber, MoveOutcome outcome) {
        String reason = "Player P" + this.getName() + " (Robot) chooses house #" + houseNumber + " because it ";
        if (outcome == MoveOutcome.ANOTHER_TURN) {
            return reason + "leads to an extra move";
        } else if (outcome == MoveOutcome.CAPTURE) {
            return reason + "leads to a capture";
        } else {
            return reason + "is the first legal move";
        }
    }
}
