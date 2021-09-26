package fsm;

public class Main {

    public static void main(String[] args) {

        StateMachine<String, String, Void> stateMachine =
                new BaseStateMachine<>("init");

        StateMachineTransitionProvider<String, String, Void> transitionProvider =
                new DefaultStateMachineTransitionProvider();

        transitionProvider.provide().forEach(s -> {
            stateMachine.addTransition(s.getTrigger(), s);
        });

        System.out.println("Current state is: " + stateMachine.getState());
        stateMachine.fire("event1").ifPresent(t -> t.getAction().perform(null));
        System.out.println("Current state is: " + stateMachine.getState());
        stateMachine.fire("event2").ifPresent(t -> t.getAction().perform(null));
        System.out.println("Current state is: " + stateMachine.getState());
        stateMachine.fire("event3").ifPresent(t -> t.getAction().perform(null));
        System.out.println("Current state is: " + stateMachine.getState());
    }
}
