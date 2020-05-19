package kalah.Display;

import kalah.Board.Board;

/**
 * An abstract interface for handling all display requests, format and show it to a user.
 */
public interface OutputAdapter {
    void displayMessage(String message);
    void displayBoard(Board board);
}


