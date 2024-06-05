package org.shadowmaster435.limbo.util;

import java.util.HashMap;

public class BindableTimerQueue {

    public Timer full_length_timer;
    public HashMap<Integer, BindableTimer> timers = new HashMap<>();

    public BindableTimerQueue(HashMap<Integer, BindableTimer> timers) {
        this.timers = timers;
    }



    public void update(Object instance) {
        for (int start_time : timers.keySet()) {
            var timer = timers.get(start_time);
            timer.update(instance);
            if (full_length_timer.wait_ticks == start_time) {
                timer.start();
            }
        }
    }

}
