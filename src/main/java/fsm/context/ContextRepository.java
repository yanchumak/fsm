package fsm.context;

import java.util.Optional;

import fsm.FiniteStateMachine;

/**
 * Repository to find and save {@link Context} of {@link FiniteStateMachine}
 *
 * @param <C> the class type of the context.
 */
public interface ContextRepository<C extends Context> {
	void save(C context) throws ContextRepositoryException;

	Optional<C> find() throws ContextRepositoryException;
}