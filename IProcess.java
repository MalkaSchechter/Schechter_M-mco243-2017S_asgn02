package processorsimulation;

/**
 * The IProcess interface represents a process allowing for modification or
 * expansion of the project and the use of mock objects during testing.
 * 
 * @author Malky
 *
 */
public interface IProcess {

	// returns the process ID
	int getPid();

	// returns the process name
	String getProcName();

	// executes instruction instruc of the process and returns the
	// ProcessState that results from that instruction's execution
	ProcessState execute(int instruc);
}
