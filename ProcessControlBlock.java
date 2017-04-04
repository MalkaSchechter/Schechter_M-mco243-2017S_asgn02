package processorsimulation;

public class ProcessControlBlock {
	private IProcess process;
	private int currInstruction;
	private int register0;
	private int register1;
	private int register2;
	private int register3;

	public ProcessControlBlock(IProcess process) {
		this.process = process;

	}

	public IProcess getProcess() {
		return process;
	}

	public int getCurrInstruction() {
		return currInstruction;
	}

	public void setCurrInstruction(int instruc) {
		currInstruction = instruc;
	}


	public int getRegister0() {
		return register0;
	}

	public void setRegister0(int val) {
		register0 = val;
	}

		public int getRegister1() {
		return register1;
	}

	public void setRegister1(int val) {
		register1 = val;
	}

	public int getRegister2() {
		return register2;
	}

	public void setRegister2(int val) {
		register2 = val;
	}
	
	public int getRegister3() {
		return register3;
	}

	public void setRegister3(int val) {
		register3 = val;
	}
}
