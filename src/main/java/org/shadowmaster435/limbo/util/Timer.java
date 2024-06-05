package org.shadowmaster435.limbo.util;

public class Timer {

    private long start_tick = 0;
    public int ticks_left = 0;

    public int wait_ticks;
    private boolean finished = false;

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

    public boolean timeout() {
        return ticks_left <= 0;
    }

    public void update() {
        if (timeout()) {
            if (repeating) {
                start(wait_ticks);
            } else {
                finished = true;
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
    }
    public void start() {
        ticks_left = wait_ticks;
        finished = false;
        wait_ticks = ticks_left;
    }

}
