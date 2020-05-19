package kalah.Player;

import com.qualitascorpus.testsupport.IO;
import kalah.Globals;

/**
 * A player that interfaces with a keyboard.
 */
public class Human extends Player{
    private final IO inputDevice;

    public Human(IO inputDevice, int id) {
        super(id);
        this.inputDevice = inputDevice;
    }

    @Override
    public int selectPit() {
        return this.inputDevice.readInteger("Player P" + this.getName() + "'s turn - Specify house number or 'q' to quit: ",
                1, Globals.NUMBER_OF_HOUSES_PER_PLAYER, -1, "q");
    }
}
