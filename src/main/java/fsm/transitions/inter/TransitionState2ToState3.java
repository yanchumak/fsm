package fsm.transitions.inter;

import fsm.BaseTransition;

public class TransitionState2ToState3 extends BaseTransition<String, String, Void> {
    public TransitionState2ToState3() {
        super("state2", "state3",
                c -> System.out.println("transtion: state2 -> state3"), "event2");
    }
}
