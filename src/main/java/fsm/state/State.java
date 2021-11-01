package fsm.state;

import java.util.ArrayList;
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
	Action<E, C> entryAction();

	/**
	 * Action that will be performed when exiting the state.
	 *
	 * @return context of {@link FiniteStateMachine}
	 */
	Action<E, C> exitAction();

	/**
	 * List of state transitions which allowed for the current state.
	 *
	 * @return collection of {@link Transition}.
	 */
	List<Transition<E, C>> transitions();

	class Builder<E extends Event, C extends Context> {
		private String name;
		private List<Transition<E, C>> transitions = new ArrayList<>();
		private Action<E, C> entryAction = Action.noAction();
		private Action<E, C> exitAction = Action.noAction();

		public Builder<E, C> withName(String name) {
			this.name = name;
			return this;
		}

		public Builder<E, C> addTransition(Transition<E, C> transition) {
			this.transitions.add(Objects.requireNonNull(transition, "transition can't be null"));
			return this;
		}

		public Builder<E, C> withEntryAction(Action<E, C> entryAction) {
			this.entryAction = entryAction;
			return this;
		}

		public Builder<E, C> withTransitions(List<Transition<E, C>> transitions) {
			this.transitions = Objects.requireNonNull(transitions, "transitions can't be null");

			return this;
		}

		public Builder<E, C> withExitAction(Action<E, C> exitAction) {
			this.exitAction = exitAction;
			return this;
		}

		public State<E, C> build() {
			Objects.requireNonNull(name, "name can't be null");
			Objects.requireNonNull(entryAction, "entry action can't be null");
			Objects.requireNonNull(exitAction, "exit action can't be null");
			return new StateImpl<>(name, transitions, entryAction, exitAction);
		}
	}

	static <E extends Event, C extends Context> Builder<E, C> builder() {
		return new Builder<>();
	}
}
