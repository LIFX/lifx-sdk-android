//
//  LFXTimerUtils.java
//  LIFX
//
//  Created by Jarrod Boyes on 24/03/14.
//  Copyright (c) 2014 LIFX Labs. All rights reserved.
//

package lifx.java.android.util;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Looper;

public class LFXTimerUtils {
    private final static String TAG = LFXTimerUtils.class.getSimpleName();

    private static class MainThreadRunnableTimerTask extends TimerTask {
        private Runnable task;

        public MainThreadRunnableTimerTask(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(task);
        }
    }

//    public static Timer getTimerTaskWithPeriod( Runnable task, long period, boolean immediate)
//    {
//        return getTimerTaskWithPeriod(task,period,immediate,"UnnamedTimerTask");
//    }

    public static Timer getTimerTaskWithPeriod(Runnable task, long period, boolean immediate, String name) {
        //LFXLog.d(TAG, "getTimerTaskWithPeriod() - " + name + ", " + period);
        Timer timer = new Timer();
        long delay = 0L;

        if (!immediate) {
            delay = period;
        }

        timer.scheduleAtFixedRate(new MainThreadRunnableTimerTask(task), delay, period);
        return timer;
    }

    public static void scheduleDelayedTask(Runnable task, long delay) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(task, delay);
    }
}
