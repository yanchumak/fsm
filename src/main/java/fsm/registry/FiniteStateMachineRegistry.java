package fsm.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import fsm.Event;
import fsm.FiniteStateMachine;
import fsm.context.Context;

/**
 * Finite state machine registry.
 *
 * @param <E> the class type of the event.
 * @param <C> the class type of the context.
 */
public interface FiniteStateMachineRegistry<E extends Event, C extends Context> {

	/**
	 * This method returns finite state machine that handles event.
	 *
	 * @param event that should be handled by finite state machine.
	 *
	 * @return {@link FiniteStateMachine} or {@link Optional#empty()} if there is no such finite state machine.
	 */
	Optional<FiniteStateMachine<E, C>> findByEvent(String event);

	/**
	 * This method returns finite state machine by name.
	 *
	 * @param name of finite state machine.
	 *
	 * @return {@link FiniteStateMachine} or {@link Optional#empty()} if there is no such finite state machine.
	 */
	Optional<FiniteStateMachine<E, C>> findByName(String name);

	class Builder<E extends Event, C extends Context> {
		private final Map<String, FiniteStateMachine<E, C>> eventToFsm = new HashMap<>();

		public Builder<E, C> addFiniteStateMachine(FiniteStateMachine<E, C> finiteStateMachine) {
			Objects.requireNonNull(finiteStateMachine, "finiteStateMachine can't be null");

			finiteStateMachine.states().stream()
					.flatMap(state -> state.transitions().stream())
					.forEach(transition -> {
						FiniteStateMachine<E, C> existed = eventToFsm.put(transition.event(), finiteStateMachine);
						if(existed != null) {
							throw new IllegalArgumentException(
									String.format("Finite state machines '%s' and '%s' handle the same event '%s'",
											existed.name(), finiteStateMachine.name(), transition.event()));
						}
					});

			long withSameName = eventToFsm.values().stream()
					.filter(f -> f.name().equals(finiteStateMachine.name()))
					.count();

			if(withSameName > 1) {
				throw new IllegalArgumentException(
						String.format("Finite state machine with name '%s' is already declared, name should be unique",
								finiteStateMachine.name()));
			}
			return this;
		}

		public FiniteStateMachineRegistry<E, C> build() {
			return new FiniteStateMachineRegistryImpl<>(eventToFsm);
		}
	}
}
