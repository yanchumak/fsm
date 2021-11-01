package fsm;

/**
 * State machine generic exception.
 */
public class FiniteStateMachineException extends Exception {
	public FiniteStateMachineException(String message) {
		super(message);
	}

	public FiniteStateMachineException(String message, Exception cause) {
		super(message, cause);
	}
}
