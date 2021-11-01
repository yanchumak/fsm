package fsm.context;

import java.util.Map;

class CurrentStateImpl implements CurrentState {
	private final String name;
	private final String finiteStateMachine;
	private final Map<String, String> values;

	CurrentStateImpl(String name, String finiteStateMachine, Map<String, String> values) {
		this.name = name;
		this.finiteStateMachine = finiteStateMachine;
		this.values = values;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String finiteStateMachine() {
		return finiteStateMachine;
	}

	@Override
	public Map<String, String> values() {
		return values;
	}
}
