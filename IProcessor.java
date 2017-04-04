package processorsimulation;
/**
 * The IProcessor interface represents a processor object while allowing for
 * future modifications.
 * 
 * @author Malky
 *
 */
public interface IProcessor {

	IProcess getProcess();

	void setProcess(IProcess process);

	int getInstruction();

	void setInstruction(int instruc);

	ProcessState executeNextInstruction();
	
	 void setRegisterValue(int location, int val);
	 
	 int getRegisterValue(int location);
}
