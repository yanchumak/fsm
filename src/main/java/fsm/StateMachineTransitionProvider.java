package fsm;

import java.util.Collection;
import java.util.Map;

public interface StateMachineTransitionProvider<E, S, C> {

    Collection<? extends Transition<E, S, C>> provide();
}
