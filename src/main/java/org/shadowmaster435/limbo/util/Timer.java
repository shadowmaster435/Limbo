package org.shadowmaster435.limbo.util;

import net.minecraft.client.render.Camera;
import org.shadowmaster435.limbo.util.callable.Callable;

public class Timer {

    private long start_tick = 0;
    public int ticks_left = 0;
    public boolean started = false;
    public int wait_ticks;
    private boolean finished = false;
    public Callable on_timeout = null;
    public boolean repeating = false;

    public Timer() {
    }

    public Timer(int ticks) {
        wait_ticks = ticks;
    }
    public Timer(int ticks, boolean repeat) {
        wait_ticks = ticks;
        repeating = repeat;
    }

    public void bind_timeout(Object caller, String method_name, Class<?> args) {
        on_timeout = new Callable(caller, method_name, args);
    }

    public boolean timeout() {
        return ticks_left <= 0;
    }

    public void update() {
        if (timeout()) {
            if (repeating) {
                start(wait_ticks);
            } else {
                finished = true;
                started = false;
            }
            if (on_timeout != null) {
                on_timeout.call();
            }
        } else {
            ticks_left -= 1;
        }
    }

    public float delta() {
        if (wait_ticks > 0 && ticks_left > 0) {
            return 1.0f - (((float) ticks_left) / ((float) wait_ticks));
        } else {
            return 1.0f;
        }
    }

    public boolean is_finished() {
        return finished;
    }

    public void start(int ticks) {
        ticks_left = ticks;
        wait_ticks = ticks;
        finished = false;
        started = true;
    }
    public void start() {
        ticks_left = wait_ticks;
        finished = false;
        wait_ticks = ticks_left;
    }

}
