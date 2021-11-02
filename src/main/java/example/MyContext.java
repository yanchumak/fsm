package example;

import java.util.HashMap;
import java.util.Map;

import fsm.context.Context;
import fsm.context.CurrentState;

class MyContext implements Context {
	final String data;
	final Map<String, CurrentState> currentStates;

	MyContext(String data, Map<String, CurrentState> currentStates) {
		this.data = data;
		this.currentStates = currentStates;
	}

	@Override
	public Map<String, CurrentState> currentStates() {
		return new HashMap<>(currentStates);
	}

	@Override
	public void updateCurrentState(String finiteStateMachineName, CurrentState currentState) {
		currentStates.put(finiteStateMachineName, currentState);
	}

	@Override
	public String toString() {
		return "MyContext{" +
				"data='" + data + '\'' +
				", currentStates=" + currentStates +
				'}';
	}
}
