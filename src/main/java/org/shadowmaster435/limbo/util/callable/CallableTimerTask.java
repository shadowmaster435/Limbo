package org.shadowmaster435.limbo.util.callable;

import io.netty.util.Timeout;

import java.util.TimerTask;

public class CallableTimerTask extends TimerTask {

    private Callable callable;
    private Object[] args;
    private boolean static_call = false;

    public CallableTimerTask(Callable callable, boolean static_call, Object... args) {
        this.callable = callable;
        this.args = args;
        this.static_call = static_call;
    }

    @Override
    public void run() {
        if (static_call) {
            callable.call_static(args);
        } else {
            callable.call(args);
        }
    }
}
