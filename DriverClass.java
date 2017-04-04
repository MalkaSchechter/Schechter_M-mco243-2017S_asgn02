package processorsimulation;
import java.util.ArrayList;
import java.util.Arrays;

public class DriverClass {

	public static void main(String[] args) {
		final int QUANTUM = 5;
		IRandomValueGenerator rand = RandomValueGenerator.getInstance();

		IProcessor processor = new ProcessorSimulator(rand);

		IProcess process1 = new ProcessSimulator(1, "process1", 100, rand);
		IProcess process2 = new ProcessSimulator(2, "process2", 200, rand);
		IProcess process3 = new ProcessSimulator(3, "process3", 300, rand);
		IProcess process4 = new ProcessSimulator(4, "process4", 425, rand);
		IProcess process5 = new ProcessSimulator(5, "process5", 150, rand);
		IProcess process6 = new ProcessSimulator(6, "process6", 200, rand);
		IProcess process7 = new ProcessSimulator(7, "process7", 330, rand);
		IProcess process8 = new ProcessSimulator(8, "process8", 400, rand);
		IProcess process9 = new ProcessSimulator(9, "process9", 210, rand);
		IProcess process10 = new ProcessSimulator(10, "process10", 300, rand);

		ProcessControlBlock PCB1 = new ProcessControlBlock(process1);
		ProcessControlBlock PCB2 = new ProcessControlBlock(process2);
		ProcessControlBlock PCB3 = new ProcessControlBlock(process3);
		ProcessControlBlock PCB4 = new ProcessControlBlock(process4);
		ProcessControlBlock PCB5 = new ProcessControlBlock(process5);
		ProcessControlBlock PCB6 = new ProcessControlBlock(process6);
		ProcessControlBlock PCB7 = new ProcessControlBlock(process7);
		ProcessControlBlock PCB8 = new ProcessControlBlock(process8);
		ProcessControlBlock PCB9 = new ProcessControlBlock(process9);
		ProcessControlBlock PCB10 = new ProcessControlBlock(process10);

		// list of ready process. Not prioritized.
		ArrayList<ProcessControlBlock> ready = new ArrayList<ProcessControlBlock>();
		// list of blocked processes
		ArrayList<ProcessControlBlock> blocked = new ArrayList<ProcessControlBlock>();

		// all processes are initially on the ready list
		ready.addAll(Arrays.asList(PCB2, PCB3, PCB4, PCB5, PCB6, PCB7, PCB8, PCB9, PCB10));

		// Set initial state of the processor
		ProcessControlBlock PCB = PCB1;

		// load new PCB register vals into processor registers
		processor.setRegisterValue(0, PCB.getRegister0());
		processor.setRegisterValue(1, PCB.getRegister1());
		processor.setRegisterValue(2, PCB.getRegister2());
		processor.setRegisterValue(3, PCB.getRegister3());

		// load current instruction to the processor
		processor.setInstruction(PCB.getCurrInstruction());

		// put on processor
		processor.setProcess(PCB.getProcess());

		// Set initial state and time of the process
		ProcessState state = ProcessState.READY;
		Integer time = 0;

		for (int i = 0; i < 3000; i++) {

			if (state == ProcessState.FINISHED) {
				System.out.println("Process completed.");

				if (moreToProcess(ready, blocked, rand)) {
					PCB = contextSwitchRestore(processor, PCB, ready);
				} else {
					System.out.println("All processes complete.");
					break;
				}
				// reset time for new process
				time = 0;

				blockedToReady30perc(blocked, ready, rand);
			}

			else if (state == ProcessState.BLOCKED) {
				System.out.println("Process blocked.");
				// move process to the blocked list
				blocked.add(PCB);

				if (moreToProcess(ready, blocked, rand)) {
					contextSwitchSave(processor, PCB, ready);
					PCB = contextSwitchRestore(processor, PCB, ready);
				}
				// reset time for new process
				time = 0;

				blockedToReady30perc(blocked, ready, rand);
			}

			else if (time == QUANTUM) {
				// if there are no other ready processes there's no need for a
				// context switch due to quantum
				if (!ready.isEmpty()) {
					// quantum has run out, put process back on ready list
					ready.add(PCB);

					System.out.println("Quantum expired.");
					contextSwitchSave(processor, PCB, ready);
					PCB = contextSwitchRestore(processor, PCB, ready);
				}
				// reset time for new process
				time = 0;

				blockedToReady30perc(blocked, ready, rand);
			}

			state = processor.executeNextInstruction();
			time++;
			blockedToReady30perc(blocked, ready, rand);
		}
	}

	public static void contextSwitchSave(IProcessor processor, ProcessControlBlock PCB,
			ArrayList<ProcessControlBlock> ready) {

		System.out.println("Context Switch - Saving " + PCB.getProcess().getProcName());

		System.out.print("Instruction " + PCB.getCurrInstruction() + " - ");
		System.out.println("R1: " + processor.getRegisterValue(0) + "  R2: " + processor.getRegisterValue(1) + "  R3: "
				+ processor.getRegisterValue(2) + "  R4: " + processor.getRegisterValue(3));

		// save vals of registers to the PCB
		PCB.setRegister0(processor.getRegisterValue(0));
		PCB.setRegister1(processor.getRegisterValue(1));
		PCB.setRegister2(processor.getRegisterValue(2));
		PCB.setRegister3(processor.getRegisterValue(3));

		// save current instruction to the PCB
		PCB.setCurrInstruction(processor.getInstruction());
	}

	public static ProcessControlBlock contextSwitchRestore(IProcessor processor, ProcessControlBlock PCB,
			ArrayList<ProcessControlBlock> ready) {

		// get next process, remove from ready list
		PCB = ready.get(0);
		ready.remove(PCB);

		System.out.println("Context Switch - Restoring " + PCB.getProcess().getProcName());

		System.out.print("Instruction " + PCB.getCurrInstruction() + " - ");
		System.out.println("R1: " + processor.getRegisterValue(0) + "  R2: " + processor.getRegisterValue(1) + "  R3: "
				+ processor.getRegisterValue(2) + "  R4: " + processor.getRegisterValue(3));

		// load new PCB register vals into processor registers
		processor.setRegisterValue(0, PCB.getRegister0());
		processor.setRegisterValue(1, PCB.getRegister1());
		processor.setRegisterValue(2, PCB.getRegister2());
		processor.setRegisterValue(3, PCB.getRegister3());

		// load current instruction to the processor
		processor.setInstruction(PCB.getCurrInstruction());

		// put on processor
		processor.setProcess(PCB.getProcess());

		return PCB;
	}

	public static void blockedToReady30perc(ArrayList<ProcessControlBlock> blocked,
			ArrayList<ProcessControlBlock> ready, IRandomValueGenerator rand) {
		ProcessControlBlock pcb;
		for (int i = 0; i < blocked.size(); i++) {
			if (rand.getTrueWithProbability(.3)) {
				pcb = blocked.get(i);
				blocked.remove(pcb);
				ready.add(pcb);
			}
		}
	}

	public static boolean moreToProcess(ArrayList<ProcessControlBlock> ready, ArrayList<ProcessControlBlock> blocked,
			IRandomValueGenerator rand) {
		if (ready.isEmpty()) {
			if (blocked.isEmpty()) {
				return false;
			} else {
				while (ready.isEmpty()) {
					blockedToReady30perc(blocked, ready, rand);
				}
			}
		}

		return true;
	}

}
