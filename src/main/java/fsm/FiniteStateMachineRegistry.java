package fsm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import fsm.context.Context;

/**
 * Finite state machine registry.
 *
 * @param <F> the class type of the finite state machine.
 */
public interface FiniteStateMachineRegistry<F extends FiniteStateMachine<? extends Event, ? extends Context>> {

	/**
	 * This method returns finite state machine that handles event.
	 *
	 * @param event that should be handled by finite state machine.
	 *
	 * @return {@link FiniteStateMachine} or {@link Optional#empty()} if there is no such finite state machine.
	 */
	Optional<F> findByEvent(String event);

	/**
	 * This method returns finite state machine by name.
	 *
	 * @param name of finite state machine.
	 *
	 * @return {@link FiniteStateMachine} or {@link Optional#empty()} if there is no such finite state machine.
	 */
	Optional<F> findByName(String name);

	/**
	 * The method returns all known finite state machines.
	 *
	 * @return list of {@link FiniteStateMachine}.
	 */
	List<F> findAll();

	static <F extends FiniteStateMachine<? extends Event, ? extends Context>> Builder<F> builder() {
		return new Builder<>();
	}

	class Builder<F extends FiniteStateMachine<? extends Event, ? extends Context>> {
		private final Map<String, F> eventToFsm = new HashMap<>();
		private final Set<String> fsmNames = new HashSet<>();

		public Builder<F> add(F finiteStateMachine) {
			Objects.requireNonNull(finiteStateMachine, "finiteStateMachine can't be null");


			if(!fsmNames.add(finiteStateMachine.name())) {
				throw new IllegalArgumentException(
						String.format("Finite state machine with name '%s' is already added, name should be unique", finiteStateMachine.name()));
			}

			finiteStateMachine.states().stream()
					.flatMap(state -> state.transitions().stream())
					.forEach(transition -> {
						F existed = eventToFsm.put(transition.event(), finiteStateMachine);
						if(existed != null && !existed.name().equals(finiteStateMachine.name())) {
							throw new IllegalArgumentException(
									String.format("Finite state machines '%s' and '%s' handle the same event '%s'",
											existed.name(), finiteStateMachine.name(), transition.event()));
						}
					});

			return this;
		}

		public FiniteStateMachineRegistry<F> build() {
			return new FiniteStateMachineRegistryImpl<>(eventToFsm);
		}
	}
}
