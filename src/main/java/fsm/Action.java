package fsm;

public interface Action<C> {
    void perform(C context);
}
