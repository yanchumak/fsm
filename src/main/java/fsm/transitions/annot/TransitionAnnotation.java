package fsm.transitions.annot;

import fsm.Action;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TransitionAnnotation {

    String source();

    String destination();

    String trigger();
}
