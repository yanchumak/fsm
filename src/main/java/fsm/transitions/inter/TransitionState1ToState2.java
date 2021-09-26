package fsm.transitions.inter;

import fsm.BaseTransition;

public class TransitionState1ToState2 extends BaseTransition<String, String, Void> {
    public TransitionState1ToState2() {
        super("state1", "state2",
                c -> System.out.println("transtion: state1 -> state2"), "event1");
    }
}
