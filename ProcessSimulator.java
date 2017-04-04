package processorsimulation;
/**
 * The ProcessSimulator class simulates a process and keeps track of its process
 * id, name, num of total instrucs, and a reference to a Random object it uses
 * for simulation purposes.
 * 
 * @author Malky
 *
 */
public class ProcessSimulator implements IProcess {
	private int procID;
	private String procName;
	private int totNumInstrucs;
	private IRandomValueGenerator rand;

	public ProcessSimulator(int procID, String procName, int totNumInstrucs, IRandomValueGenerator rand) {
		this.procID = procID;
		this.procName = procName;
		this.totNumInstrucs = totNumInstrucs;
		this.rand = rand;
	}

	@Override
	public int getPid() {
		// TODO Auto-generated method stub
		return procID;
	}

	@Override
	public String getProcName() {
		return procName;
	}

	/**
	 * The execute method first displays relevant info about the instruc being
	 * executed, then if instruc is greater than or equal to the total
	 * instructions the Process state is FINISHED. Otherwise the process blocks
	 * with 15% probability. If it does not block it remains READY.
	 * 
	 * @return the process state after the instruction has been executed
	 */
	@Override
	public ProcessState execute(int instruc) {
		// display relevant info
		StringBuilder sb = new StringBuilder();
		sb.append("Process ID: ");
		sb.append(this.procID);
		sb.append("\tProcess Name: ");
		sb.append(this.procName);
		sb.append("\tInstruction Number: ");
		sb.append(instruc);

		System.out.println(sb.toString());

		if (instruc >= this.totNumInstrucs) {
			return ProcessState.FINISHED;
		}

		else if (rand.getTrueWithProbability(.15)) {
			return ProcessState.BLOCKED;
		}

		else {
			return ProcessState.READY;
		}

	}

}
