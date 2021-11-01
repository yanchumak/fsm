package fsm.context;

import fsm.FiniteStateMachineException;

/**
 * Exception that can be thrown when {@link ContextRepository} methods are called.
 */
public class ContextRepositoryException extends FiniteStateMachineException {
	public ContextRepositoryException(String message) {
		super(message);
	}

	public ContextRepositoryException(String message, Exception cause) {
		super(message, cause);
	}
}
