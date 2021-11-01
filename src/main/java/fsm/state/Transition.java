package fsm.state;

import java.util.Objects;
import java.util.Optional;

import fsm.Event;
import fsm.FiniteStateMachine;
import fsm.context.Context;

/**
 * An interface that represents a transition between states.
 *
 * @param <E> the class type of the event.
 * @param <C> the class type of the context.
 */
public interface Transition<E extends Event, C extends Context> {

	/**
	 * The name of event that triggers transition between states. {@link fsm.Event}.
	 *
	 * @return event name for triggering.
	 */
	String event();

	/**
	 * The name of the next state.
	 *
	 * @return name of the next state.
	 */
	String nextState();

	/**
	 * An action that will be performed on the transition between states.
	 *
	 * @return context of {@link FiniteStateMachine}
	 */
	Action<E, C> action();

	/**
	 * This method returns condition based on which state will be transitioned.
	 *
	 * @return instance of {@link Condition}.
	 */
	Condition<C> condition();

	class Builder<E extends Event, C extends Context> {
		private String event;
		private String nextState;
		private Action<E, C> action = Action.noAction();
		private Condition<C> condition = Condition.noCondition();

		public Builder<E, C> withEvent(String event) {
			this.event = event;
			return this;
		}

		public Builder<E, C> withNextState(String nextState) {
			this.nextState = nextState;
			return this;
		}

		public Builder<E, C> withAction(Action<E, C> action) {
			this.action = Objects.requireNonNull(action, "action can't be null");
			return this;
		}

		public Builder<E, C> withCondition(Condition<C> condition) {
			this.condition = Objects.requireNonNull(condition, "condition can't be null");
			return this;
		}

		public Transition<E, C> build() {
			Objects.requireNonNull(event, "event can't be null");
			Objects.requireNonNull(nextState, "nextState can't be null");
			return new TransitionImpl<>(event, nextState, action, condition);
		}
	}

	static <E extends Event, C extends Context> Transition.Builder<E, C> builder() {
		return new Transition.Builder<>();
	}
}
