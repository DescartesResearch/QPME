package de.tud.cs.simqpn.kernel.executor.parallel;

public class LPTester {
	public static void main(String[] args) {
		circleScenario();
	}
	
	private static void circleScenario(){
		LP LP1 = new LP();
		LP LP2 = new LP();
		LP LP3 = new LP();
		
		LP1.incommingTransitions = LP3.outgoingTransitions;
		LP2.incommingTransitions = LP1.outgoingTransitions;
		LP3.incommingTransitions = LP2.outgoingTransitions;
		
		
		
	}

}
