package fsm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fsm.context.Context;
import fsm.state.State;

/**
 * The operational state machine interface.
 *
 * @param <E> the class type of the events.
 * @param <C> the class type of the context.
 */
public interface FiniteStateMachine<E extends Event, C extends Context> {

	/**
	 * It returns name of init state of the finite state machine.
	 *
	 * @return name of init state.
	 */
	String initState();

	/**
	 * Name of the current state machine.
	 *
	 * @return state machine name.
	 */
	String name();

	/**
	 * List of allowed states of the current state machine.
	 *
	 * @return collection of {@link State}
	 */
	List<State<E, C>> states();

	/**
	 * Fires the specified event. This is how states are transitioned.
	 *
	 * @param event the event fired.
	 * @param context the context of the current state machine.
	 *
	 * @throws FiniteStateMachineException
	 */
	void fire(E event, C context) throws FiniteStateMachineException;

	static <E extends Event, C extends Context> Builder<E, C> builder() {
		return new Builder<>();
	}

	class Builder<E extends Event, C extends Context> {
		private String name;
		private List<State<E, C>> states = new ArrayList<>();
		private String initState;

		public Builder<E, C> withName(String name) {
			this.name = name;
			return this;
		}
		public Builder<E, C> withInitState(String initState) {
			this.initState = initState;
			return this;
		}

		public Builder<E, C> withStates(List<State<E, C>> states) {
			this.states = states;
			return this;
		}

		public Builder<E, C> addState(State<E, C> state) {
			Objects.requireNonNull(states, "state can't be null");
			this.states.add(state);
			return this;
		}

		public FiniteStateMachine<E, C> build() {
			Objects.requireNonNull(states, "states can't be null");
			Objects.requireNonNull(name, "name can't be null");
			Objects.requireNonNull(initState, "initState can't be null");
			return new FiniteStateMachineImpl<>(name, states, initState);
		}
	}

}
