package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.Board.Board;
import kalah.Display.ConsoleDisplay;
import kalah.Display.OutputAdapter;
import kalah.Display.VerticalConsoleDisplay;
import kalah.Player.Human;
import kalah.Player.Player;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Holds the playing board and player.
 * In charge of asking the correct player to play and passes messages between players, boards and output.
 */
public class GameManager {
    private final static int QUIT_INDICATOR = -1;

    private final Board board;
    private final ArrayList<Player> players;
    private int currentPlayerIndex;
    private final OutputAdapter output;

    public GameManager(IO io) {
        this.players = new ArrayList<Player>(Arrays.asList(new Human(io, 0), new Human(io, 1)));
        this.currentPlayerIndex = 0;
        this.board = new Board(this.players);
        this.output = new ConsoleDisplay(io);
    }

    public void play() {
        this.output.displayBoard(this.board, this.players);

        while (true) {

            if(!this.board.moveAvailable(this.players.get(this.currentPlayerIndex))){
                this.gameOver();
                this.calculateScores();
                return;
            }

            int pitIndex = this.players.get(currentPlayerIndex).selectPit();
            if (pitIndex == QUIT_INDICATOR) {
                this.gameOver();
                return;
            }

            if (!this.board.validSelection(pitIndex, players.get(currentPlayerIndex))) {
                output.displayMessage("House is empty. Move again.");
                this.output.displayBoard(this.board, this.players);
                continue;
            }

            boolean turnComplete = board.sowSeeds(players.get(currentPlayerIndex), pitIndex);
            this.output.displayBoard(this.board, this.players);

            if (turnComplete) {
                nextPlayer();
            }
        }
    }

    private void nextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
    }

    private void calculateScores() {
        Player winner = this.players.get(0);
        int highScore = 0;
        boolean tie = true;

        for (Player player : this.players) {
            int playerScore = this.board.calculateScore(player);
            this.output.displayMessage("\tplayer " + player.getName() + ":" + playerScore);
            if (player != winner && playerScore != highScore) {
                tie = false;
            }

            if (playerScore > highScore) {
                highScore = playerScore;
                winner = player;
            }
        }

        if (tie) {
            this.output.displayMessage("A tie!");
        } else {
            this.output.displayMessage("Player " + winner.getName() + " wins!");
        }
    }

    public void gameOver(){
        this.output.displayMessage("Game over");
        this.output.displayBoard(this.board, this.players);
    }
}
