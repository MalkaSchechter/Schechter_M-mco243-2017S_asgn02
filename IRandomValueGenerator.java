package processorsimulation;

/**
 * The IRandomValueGenerator interface acts as a wrapper allowing for
 * modification or expansion and the use of mock objects during testing.
 * 
 * @author Malky
 *
 */
public interface IRandomValueGenerator {

	int getNextInt();

	boolean getTrueWithProbability(double prob);

}
