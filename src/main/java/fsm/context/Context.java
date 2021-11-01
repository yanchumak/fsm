package fsm.context;

import java.util.Map;

/**
 * Context of state machine. It is used to keep finite state machines details such as current state name and its values.
 * It is not designed to be thread safe.
 */
public interface Context {

	/**
	 * The method returns copy of current states for all finite state machines. This read only operation.
	 *
	 * @return map where is key is finite state machine name and value is {@link CurrentState} of this finite state machine.
	 */
	Map<String, CurrentState> currentStates();

	/**
	 * The method adds or updates {@link CurrentState} for particular finite state machine.
	 *
	 * @param finiteStateMachineName name of finite state machine for which current state will be updated.
	 * @param currentState updated {@link CurrentState} object.
	 */
	void updateCurrentState(String finiteStateMachineName, CurrentState currentState);
}
