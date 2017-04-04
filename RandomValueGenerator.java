package processorsimulation;
import java.util.Random;

/**
 * The RandomValueGenerator class is a thread-safe singleton class that
 * implements the IRandomValueGenerator interface.
 * 
 * @author Malky
 *
 */
public class RandomValueGenerator implements IRandomValueGenerator {

	private Random rand;
	private volatile static RandomValueGenerator uniqueInstance = null;

	private RandomValueGenerator() {
		rand = new Random(System.currentTimeMillis());
	}

	public static RandomValueGenerator getInstance() {
		if (uniqueInstance == null) {
			synchronized (RandomValueGenerator.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new RandomValueGenerator();
				}
			}
		}
		return uniqueInstance;
	}

	@Override
	public int getNextInt() {
		return rand.nextInt();
	}

	@Override
	public boolean getTrueWithProbability(double prob) {
		return rand.nextInt(100) + 1 <= prob * 100;
	}

}
