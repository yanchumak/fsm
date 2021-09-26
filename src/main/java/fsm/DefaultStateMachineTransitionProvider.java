package fsm;

import javax.swing.plaf.synth.ColorType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultStateMachineTransitionProvider implements StateMachineTransitionProvider <String, String, Void>{

    @Override
    public Collection<? extends Transition<String, String, Void>> provide() {
        Collection<Transition<String, String, Void>> transitions = new ArrayList<>();
        transitions.add(
                new BaseTransition<>("state1", "state2", c -> {
                    System.out.println("event 1: transition state1 -> state2");
                }, "event1"));

        transitions.add(
                new BaseTransition<>("state2", "state3", c -> {
                    System.out.println("event 2: transition state2 -> state3");
                }, "event2"));

        transitions.add(
                new BaseTransition<>("state3", "state1", c -> {
                    System.out.println("event 3: transition state3 -> state1");
                },"event3"));
        return transitions;
    }
}
