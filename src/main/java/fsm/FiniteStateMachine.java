package fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
	 * It returns parent of finite state machine.
	 *
	 * @return wrapped {@link FiniteStateMachine} or {@link Optional#empty()} if finite state machine doesn't have a parent.
	 */
	Optional<FiniteStateMachine<E, C>> parent();

	/**
	 * The method returns children finite state machines.
	 *
	 * @return children {@link FiniteStateMachine} for the finite state machine.
	 */
	List<FiniteStateMachine<E, C>> children();

	/**
	 * The method returns child finite state machine with particular name.
	 *
	 * @param finiteStateMachineName Finite state machine name.
	 *
	 * @return wrapped {@link FiniteStateMachine} or {@link Optional#empty()} if there is no child finite state machine with such name.
	 */
	Optional<FiniteStateMachine<E, C>> childWithName(String finiteStateMachineName);

	/**
	 * The method adds child to the finite state machine.
	 *
	 * @param child {@link FiniteStateMachine}.
	 */
	void addChild(FiniteStateMachine<E, C> child);

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
		private FiniteStateMachine<E, C> parent;
		private String name;
		private Map<String, FiniteStateMachine<E, C>> children = new HashMap<>();
		private List<State<E, C>> states = new ArrayList<>();
		private String initState;

		public Builder<E, C> withName(String name) {
			this.name = name;
			return this;
		}

		public Builder<E, C> withParent(FiniteStateMachine<E, C> parent) {
			this.parent = parent;
			return this;
		}

		public Builder<E, C> addChild(FiniteStateMachine<E, C> child) {
			Objects.requireNonNull(child, "child can't be null");
			if(children.put(child.name(), child) != null) {
				throw new IllegalArgumentException(
						String.format("Child finite state machine with name '%s' is already added", child.name()));
			}
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

			FiniteStateMachineImpl<E, C> instance = new FiniteStateMachineImpl<>();
			instance.parent = parent;
			instance.children = children;
			instance.initState = initState;
			instance.name = name;
			instance.states = states;

			return instance;
		}
	}

}
