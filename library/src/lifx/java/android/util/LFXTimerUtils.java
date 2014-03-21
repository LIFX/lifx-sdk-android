package lifx.java.android.util;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Looper;

public class LFXTimerUtils
{
	private static class MainThreadRunnableTimerTask extends TimerTask
	{
		private Runnable task;
		
		public MainThreadRunnableTimerTask( Runnable task)
		{
			this.task = task;
		}
		
		@Override
		public void run()
		{
			Handler handler = new Handler( Looper.getMainLooper());
			handler.post( task);
		}
	}
	
	public static Timer getTimerTaskWithPeriod( Runnable task, long period)
	{
		Timer timer = new Timer();
	    timer.schedule( new MainThreadRunnableTimerTask( task), 0, period);
	    return timer;
	}
	
	public static void scheduleDelayedTask( Runnable task, long delay)
	{
		Handler handler = new Handler( Looper.getMainLooper());
		handler.postDelayed( task, delay);
	}
}
