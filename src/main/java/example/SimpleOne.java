package example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fsm.FiniteStateMachine;
import fsm.FiniteStateMachineException;
import fsm.state.State;
import fsm.state.Transition;

public class SimpleOne {
	public static void main(String[] args) {
		List<State<MyEvent, MyContext>> states = new ArrayList<>();
		states.add(State.<MyEvent, MyContext>builder()
				.withName("INIT")
				.addTransition(Transition.<MyEvent, MyContext>builder()
						.withEvent("EVENT_1")
						.withNextState("STATE_2")
						.withAction((e, c, f) -> System.out.println("Action INIT -> STATE_2")).build())
				.withEntryAction((e, c, f) -> System.out.println("Entry INIT"))
				.withExitAction((e, c, f) -> System.out.println("Exit INIT"))
				.build());

		states.add(State.<MyEvent, MyContext>builder()
				.withName("STATE_2")
				.addTransition(Transition.<MyEvent, MyContext>builder()
						.withEvent("EVENT_FINAL")
						.withNextState("STATE_FINAL")
						.withAction((e, c, f) -> System.out.println("Action STATE_2 -> STATE_FINAL")).build())
				.withEntryAction((e, c, f) -> System.out.println("Entry STATE_2"))
				.withExitAction((e, c, f) -> System.out.println("Exit STATE_2"))
				.build());

		states.add(State.<MyEvent, MyContext>builder()
				.withName("STATE_FINAL")
				.withEntryAction((e, c, f) -> System.out.println("Entry STATE_FINAL"))
				.withExitAction((e, c, f) -> System.out.println("Exit STATE_FINAL"))
				.build());

		FiniteStateMachine<MyEvent, MyContext> main = FiniteStateMachine.<MyEvent, MyContext>builder()
				.withName("MAIN")
				.withInitState("INIT")
				.withStates(states).build();

		try {
			MyContext context = new MyContext("context", new HashMap<>());
			main.handle(new MyEvent("EVENT_1", "data1"), context);
			main.handle(new MyEvent("EVENT_FINAL", "data2"), context);
			System.out.println(context.currentStates());
		} catch(FiniteStateMachineException e) {
			e.printStackTrace();
		}

	}
}
