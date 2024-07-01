package org.shadowmaster435.limbo.util.callable;

import org.jetbrains.annotations.Nullable;

public record ConditionalCallable(Callable do_if, Condition condition, @Nullable Callable otherwise) {

    public static ConditionalCallable create(Object caller, String do_if, String condition) {
        return new ConditionalCallable(new Callable(caller, do_if), new Condition(condition), null);
    }

    public static ConditionalCallable create(Object caller, String do_if, String condition, String otherwise) {
        return new ConditionalCallable(new Callable(caller, do_if), new Condition(condition), new Callable(caller, otherwise));
    }

    public Object try_run() {
        if (condition.test()) {
            return do_if.call();
        } else if (otherwise != null) {
            return otherwise.call();
        }
        return null;
    }

    public <T> T try_run_casted() {
        if (condition.test()) {
            return (T) do_if.call();
        } else if (otherwise != null) {
            return (T) otherwise.call();
        }
        return null;
    }

}
