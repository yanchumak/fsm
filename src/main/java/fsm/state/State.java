package fsm.state;

import java.util.List;
import java.util.Objects;

import fsm.Event;
import fsm.FiniteStateMachine;
import fsm.context.Context;

/**
 * Representation of state for state machine.
 *
 * @param <E> the class type of the event.
 * @param <C> the class type of the context.
 */
public interface State<E extends Event, C extends Context> {

	/**
	 * Name of the state.
	 *
	 * @return state name
	 */
	String name();

	/**
	 * Action that will be performed when entering the state.
	 *
	 * @return context of {@link FiniteStateMachine}
	 */
	default Action<E, C> entryAction() {
		return Action.noAction();
	}

	/**
	 * Action that will be performed when exiting the state.
	 *
	 * @return context of {@link FiniteStateMachine}
	 */
	default Action<E, C> exitAction() {
		return Action.noAction();
	}

	/**
	 * List of state transitions which allowed for the current state.
	 *
	 * @return collection of {@link Transition}.
	 */
	List<Transition<E, C>> transitions();

	class Builder<E extends Event, C extends Context> {
		private final StateImpl<E, C> state = new StateImpl<>();

		public Builder<E, C> withName(String name) {
			if(name == null || name.isEmpty()) {
				throw new IllegalArgumentException("name can't be null or empty");
			}
			state.name = name;
			return this;
		}

		public Builder<E, C> addTransition(Transition<E, C> transition) {
			state.transitions.add(Objects.requireNonNull(transition, "transition can't be null"));
			return this;
		}

		public Builder<E, C> withEntryAction(Action<E, C> entryAction) {
			state.entryAction = Objects.requireNonNull(entryAction, "entry action can't be null");
			return this;
		}

		public Builder<E, C> withExitAction(Action<E, C> exitAction) {
			state.exitAction = Objects.requireNonNull(exitAction, "exit action can't be null");
			return this;
		}

		public State<E, C> build() {
			if(state.name == null || state.name.isEmpty()) {
				throw new IllegalArgumentException("name can't be null or empty");
			}
			if(state.transitions == null || state.transitions.isEmpty()) {
				throw new IllegalArgumentException("transitions can't be null or empty");
			}
			return state;
		}
	}

	static <E extends Event, C extends Context> Builder<E, C> builder() {
		return new Builder<>();
	}
}
