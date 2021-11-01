package fsm.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Current state of particular state machine.
 */
public interface CurrentState {

	/**
	 * The current state name.
	 *
	 * @return name of state.
	 */
	String name();

	/**
	 * Finite state machine name.
	 *
	 * @return name of finite state machine.
	 */
	String finiteStateMachine();

	/**
	 * Finite state machine values.
	 *
	 * @return values of state machine.
	 */
	default Map<String, String> values() {
		return new HashMap<>();
	}

	static CurrentState of(String name, String finiteStateMachine, Map<String, String> values) {
		return new CurrentStateImpl(name, finiteStateMachine, values);
	}

	static CurrentState of(String name, String finiteStateMachine) {
		return new CurrentStateImpl(name, finiteStateMachine, new HashMap<>());
	}
}