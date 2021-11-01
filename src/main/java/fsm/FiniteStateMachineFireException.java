package fsm;

import fsm.context.Context;

/**
 * Exception that can be thrown during calling {@link FiniteStateMachine#fire(Event, Context)}.
 */
public class FiniteStateMachineFireException extends FiniteStateMachineException {

	public FiniteStateMachineFireException(String message) {
		super(message);
	}

	public FiniteStateMachineFireException(String message, Exception cause) {
		super(message, cause);
	}
}
