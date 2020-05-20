package kalah.Display;

import kalah.Board.Board;
import kalah.Player.Player;

import java.util.ArrayList;

/**
 * An abstract interface for handling all display requests, format and show it to a user.
 */
public interface OutputAdapter {
    void displayMessage(String message);
    void displayBoard(final Board board, final ArrayList<Player> players);
}