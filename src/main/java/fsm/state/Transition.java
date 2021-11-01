package fsm.state;

import java.util.Objects;

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
	default Action<E, C> action() {
		return Action.noAction();
	}

	/**
	 * This method returns condition based on which state will be transitioned.
	 *
	 * @return instance of {@link Condition}.
	 */
	default Condition<C> condition() {
		return Condition.noCondition();
	}


	class Builder<E extends Event, C extends Context> {
		private final TransitionImpl<E, C> transition = new TransitionImpl<>();

		public Transition.Builder<E, C> withEvent(String event) {
			if(event == null || event.isEmpty()) {
				throw new IllegalArgumentException("event can't be null or empty");
			}
			transition.event = event;
			return this;
		}

		public Transition.Builder<E, C> withNextState(String nextState) {
			if(nextState == null || nextState.isEmpty()) {
				throw new IllegalArgumentException("nextState can't be null or empty");
			}
			transition.nextState = nextState;
			return this;
		}

		public Transition.Builder<E, C> withAction(Action<E, C> action) {
			transition.action = Objects.requireNonNull(action, "action can't be null");
			return this;
		}

		public Transition.Builder<E, C> withCondition(Condition<C> condition) {
			transition.condition = Objects.requireNonNull(condition, "condition can't be null");
			return this;
		}

		public Transition<E, C> build() {
			if(transition.event == null || transition.event.isEmpty()) {
				throw new IllegalArgumentException("event can't be null or empty");
			}
			if(transition.nextState == null || transition.nextState.isEmpty()) {
				throw new IllegalArgumentException("nextState can't be null or empty");
			}
			return transition;
		}
	}

	static <E extends Event, C extends Context> Transition.Builder<E, C> builder() {
		return new Transition.Builder<>();
	}
}
