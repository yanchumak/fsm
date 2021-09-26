package fsm.transitions.inter;

import fsm.BaseTransition;

public class TransitionState3ToState1 extends BaseTransition<String, String, Void> {
    public TransitionState3ToState1() {
        super("state3", "state1",
                c -> System.out.println("transition: state3 -> state1"), "event3");
    }
}
