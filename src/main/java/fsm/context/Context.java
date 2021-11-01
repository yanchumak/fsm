package fsm.context;

import java.util.List;
import java.util.Optional;

/**
 * Context of state machine. It is used to keep information about active state machines.
 * It is not thread safe.
 */
public interface Context {


	/**
	 * The method returns current states for all active finite state machines.
	 *
	 * @return list of {@link CurrentState}
	 */
	List<CurrentState> currentStates();

	/**
	 * The method returns {@link CurrentState} by finite state machine name. The name should be in correct case.
	 *
	 * @param finiteStaMachineName Finite state machine name.
	 *
	 * @return {@link Optional#empty()} if there is no such finite state machine or {@link CurrentState}.
	 */
	default Optional<CurrentState> findFor(String finiteStaMachineName) {
		return currentStates().stream()
				.filter(c -> c.name().equals(finiteStaMachineName))
				.findFirst();
	}
}
