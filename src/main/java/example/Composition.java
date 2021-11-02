package example;

import java.util.HashMap;

import fsm.FiniteStateMachine;
import fsm.FiniteStateMachineException;
import fsm.registry.FiniteStateMachineRegistry;
import fsm.state.State;
import fsm.state.Transition;

/* ====================Output=======================
	Exit MAIN_INIT
	Transition action MAIN_INIT -> MAIN_STATE_2
	Entry MAIN_STATE_2
	   Exit CHILD_INIT
	   Transition action CHILD_INIT -> CHILD_STATE_2
	   Entry CHILD_STATE_2
	   Exit CHILD_STATE_2
	   Action CHILD_STATE_2 -> CHILD_STATE_FINAL
	   Entry CHILD_STATE_FINAL
	Exit MAIN_STATE_2
	Transition action MAIN_STATE_2 -> MAIN_STATE_FINAL
	Entry MAIN_STATE_FINAL
 */
public class Composition {
	public static void main(String[] args) throws FiniteStateMachineException {
		FiniteStateMachine<MyEvent, MyContext> main = FiniteStateMachine.<MyEvent, MyContext>builder()
				.withName("MAIN")
				.withInitState("MAIN_INIT")
				.addState(State.<MyEvent, MyContext>builder()
						.withName("MAIN_INIT")
						.withEntryAction((e, c, f) -> System.out.println("Entry MAIN_INIT"))
						.addTransition(Transition.<MyEvent, MyContext>builder()
								.withEvent("MAIN_EVENT_1")
								.withNextState("MAIN_STATE_2")
								.withAction((e, c, f) -> System.out.println("Transition action MAIN_INIT -> MAIN_STATE_2")).build())
						.withExitAction((e, c, f) -> System.out.println("Exit MAIN_INIT"))
						.build())
				.addState(State.<MyEvent, MyContext>builder()
						.withName("MAIN_STATE_2")
						.withEntryAction(Composition::startChild)
						.addTransition(Transition.<MyEvent, MyContext>builder()
								.withEvent("MAIN_EVENT_FINAL")
								.withNextState("MAIN_STATE_FINAL")
								.withAction((e, c, f) -> System.out.println("Transition action MAIN_STATE_2 -> MAIN_STATE_FINAL")).build())
						.withExitAction((e, c, f) -> System.out.println("Exit MAIN_STATE_2"))
						.build())
				.addState(State.<MyEvent, MyContext>builder()
						.withName("MAIN_STATE_FINAL")
						.withEntryAction((e, c, f) -> System.out.println("Entry MAIN_STATE_FINAL"))
						.withExitAction((e, c, f) -> System.out.println("MAIN_Exit STATE_FINAL"))
						.build())
				.build();


		FiniteStateMachine<MyEvent, MyContext> child = FiniteStateMachine.<MyEvent, MyContext>builder()
				.withName("CHILD")
				.withParent(main)
				.withInitState("CHILD_INIT")
				.addState(State.<MyEvent, MyContext>builder()
						.withName("CHILD_INIT")
						.withEntryAction((e, c, f) -> System.out.println("  Entry CHILD_INIT"))
						.addTransition(Transition.<MyEvent, MyContext>builder()
								.withEvent("CHILD_EVENT_1")
								.withNextState("CHILD_STATE_2")
								.withAction((e, c, f) -> System.out.println("   Transition action CHILD_INIT -> CHILD_STATE_2")).build())
						.withExitAction((e, c, f) -> System.out.println("   Exit CHILD_INIT"))
						.build())
				.addState(State.<MyEvent, MyContext>builder()
						.withName("CHILD_STATE_2")
						.withEntryAction((e, c, f) -> System.out.println("   Entry CHILD_STATE_2"))
						.addTransition(Transition.<MyEvent, MyContext>builder()
								.withEvent("CHILD_EVENT_FINAL")
								.withNextState("CHILD_STATE_FINAL")
								.withAction((e, c, f) -> System.out.println("   Action CHILD_STATE_2 -> CHILD_STATE_FINAL")).build())
						.withExitAction((e, c, f) -> System.out.println("   Exit CHILD_STATE_2"))
						.build())
				.addState(State.<MyEvent, MyContext>builder()
						.withName("CHILD_STATE_FINAL")
						.withEntryAction(Composition::childStateFinalAction)
						.withExitAction((e, c, f) -> System.out.println("   Exit CHILD_STATE_FINAL"))
						.build())
				.build();

		main.addChild(child);

		FiniteStateMachineRegistry<MyEvent, MyContext> registry =
				FiniteStateMachineRegistry.<MyEvent, MyContext>builder()
						.add(main)
						.add(child).build();

		MyContext myContext = new MyContext("my context data", new HashMap<>());
		MyEvent myEvent1 = new MyEvent("MAIN_EVENT_1", "MAIN_EVENT_1 data");
		MyEvent myEvent2 = new MyEvent("CHILD_EVENT_FINAL", "CHILD_EVENT_FINAL data");

		registry.findByEvent(myEvent1.name()).get().fire(myEvent1, myContext);
		registry.findByEvent(myEvent2.name()).get().fire(myEvent2, myContext);
	}

	private static void startChild(MyEvent myEvent, MyContext myContext, FiniteStateMachine<MyEvent, MyContext> self) {
		FiniteStateMachine<MyEvent, MyContext> child = self.childWithName("CHILD").get();
		System.out.println("Entry MAIN_STATE_2");
		try {
			child.fire(new MyEvent("CHILD_EVENT_1", myEvent.data), myContext);
		} catch(FiniteStateMachineException e) {
			e.printStackTrace();
		}
	}

	private static void childStateFinalAction(MyEvent myEvent, MyContext myContext, FiniteStateMachine<MyEvent, MyContext> self) {
		System.out.println("   Entry CHILD_STATE_FINAL");
		FiniteStateMachine<MyEvent, MyContext> parent = self.parent().get();
		try {
			parent.fire(new MyEvent("MAIN_EVENT_FINAL", myEvent.data), myContext);
		} catch(FiniteStateMachineException e) {
			e.printStackTrace();
		}
	}

}
