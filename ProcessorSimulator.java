package processorsimulation;
public class ProcessorSimulator implements IProcessor {
	private IRandomValueGenerator rand;
	private IProcess currProcess;
	private int[] registers;
	private int currInstruction;

	public ProcessorSimulator(IRandomValueGenerator rand) {
		this.rand = rand;
		this.registers = new int[4];
		this.currInstruction = 0;
	}

	@Override
	public IProcess getProcess() {
		return currProcess;
	}

	@Override
	public void setProcess(IProcess process) {
		currProcess = process;
	}

	@Override
	public int getInstruction() {
		return currInstruction;
	}

	@Override
	public void setInstruction(int instruc) {
		currInstruction = instruc;
	}

	@Override
	public ProcessState executeNextInstruction() {
		return currProcess.execute(currInstruction++);
	}

	@Override
	public void setRegisterValue(int location, int val) {
		registers[location] = val;
	}

	/**
	 * The getRegisterValue method returns a random value for simulation
	 * purposes, instead of the actual values stored in the registers.
	 * 
	 */
	@Override
	public int getRegisterValue(int location) {
		return rand.getNextInt();
	}

}
