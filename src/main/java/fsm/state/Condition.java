package fsm.state;

import fsm.context.Context;

/**
 * TODO
 * @param <C>
 */
@FunctionalInterface
public interface Condition<C extends Context> {

	boolean test(C context);

	static <C extends Context> Condition<C> noCondition() {
		return c -> true;
	}

}
