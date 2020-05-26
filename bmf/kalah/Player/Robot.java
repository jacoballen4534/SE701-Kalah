package kalah.Player;

import kalah.Board.Board;
import kalah.Board.MoveOutcome;
import kalah.Display.OutputAdapter;
import kalah.Globals;

import java.util.ArrayList;

public class Robot extends Player{

    private OutputAdapter output;
    Board testingBoard;
    Board boardToCopyFrom;

    public Robot(int id, OutputAdapter output) {
        super(id);
        this.output = output;
    }

    public void setupTestingBoard(ArrayList<Player> players, Board realBoard) {
        this.testingBoard = new Board(players);
        this.boardToCopyFrom = realBoard;
    }

    private String createMoveExplanationString(int houseNumber, MoveOutcome outcome) {
        String explanation = "Player P" + this.getName() + " (Robot) chooses house #" + houseNumber + " because ";

        if (outcome == MoveOutcome.ANOTHER_TURN) {
            return explanation + "it leads to an extra move";
        } else if (outcome == MoveOutcome.CAPTURE) {
            return  explanation + "it leads to a capture";
        } else {
            return explanation + "it is the first legal move";
        }
    }

    @Override
    public int selectPit() {
        for (MoveOutcome desiredOutcome : MoveOutcome.values()) {
            for (int move = 1; move <= Globals.NUMBER_OF_HOUSES_PER_PLAYER; move++) {
                this.testingBoard.copySeedArrangement(this.boardToCopyFrom);
                if (!this.testingBoard.validSelection(move, this)) {
                    continue;
                }
                MoveOutcome outcome = this.testingBoard.sowSeeds(this, move);
                if (outcome == desiredOutcome) {
                    this.output.displayMessage(this.createMoveExplanationString(move, outcome));
                    return move;
                }
            }
        }
        return -1; // This should never be reached, as moveAvailable is checked before calling selectPit.
    }
}
