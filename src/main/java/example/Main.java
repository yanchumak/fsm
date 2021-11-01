package example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fsm.Event;
import fsm.FiniteStateMachineBase;
import fsm.FiniteStateMachineFireException;
import fsm.context.Context;
import fsm.context.CurrentState;
import fsm.state.State;
import fsm.state.Transition;

public class Main {
	public static void main(String[] args) {
		List<State<MyEvent, MyContext>> states = new ArrayList<>();
		states.add(State.<MyEvent, MyContext>builder()
				.withName("INIT")
				.addTransition(Transition.<MyEvent, MyContext>builder()
						.withEvent("EVENT_1")
						.withNextState("STATE_2")
						.withAction((e, c) -> {
							try {
								Thread.sleep(100);
							} catch(InterruptedException ex) {
								ex.printStackTrace();
							}
							System.out.println("action EVENT 1");
							return c;
						}).build())
				.withEntryAction((e, c) -> {
					System.out.println("Entry action INIT");
					return c;
				}).withExitAction((e, c) -> {
					System.out.println("Exit action INIT");
					return c;
				}).build());

		states.add(State.<MyEvent, MyContext>builder()
				.withName("STATE_2")
				.addTransition(Transition.<MyEvent, MyContext>builder()
						.withEvent("EVENT_2")
						.withNextState("STATE_2")
						.withAction((e, c) -> {
							try {
								Thread.sleep(100);
							} catch(InterruptedException ex) {
								ex.printStackTrace();
							}
							System.out.println("action EVENT 2");
							return c;
						}).build())
				.withEntryAction((e, c) -> {
					System.out.println("Entry action STATE_2");
					return c;
				}).withExitAction((e, c) -> {
					System.out.println("Exit action STATE_2");
					return c;
				}).build());
		FiniteStateMachineBase<MyEvent, MyContext> main = new FiniteStateMachineBase<>("MAIN", states, "INIT");

		try {
			MyContext c = main.fire(new MyEvent("EVENT_1", "data1"),
					new MyContext("context", new ArrayList<>()));

			c = main.fire(new MyEvent("EVENT_2", "data1"), c);
			System.out.println(c);
		} catch(FiniteStateMachineFireException e) {
			e.printStackTrace();
		}

	}


	static class MyContext implements Context {
		final String data;
		final List<CurrentState> currentStates;

		MyContext(String data, List<CurrentState> currentStates) {
			this.data = data;
			this.currentStates = currentStates;
		}

		@Override
		public List<CurrentState> currentStates() {
			return currentStates;
		}

	}

	static class MyEvent implements Event {
		final String name;
		final String data;

		MyEvent(String name, String data) {
			this.name = name;
			this.data = data;
		}

		@Override
		public String name() {
			return name;
		}
	}
}
