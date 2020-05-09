package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {

    public void play(IO io) {
        GameManager gameManager = new GameManager(io);
		gameManager.play();
	}



	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
}
