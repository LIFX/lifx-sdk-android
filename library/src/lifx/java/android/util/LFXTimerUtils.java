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
	
	public static Timer getTimerTaskWithPeriod( Runnable task, long period, boolean immediate)
	{		
		Timer timer = new Timer();
		long delay = 0L;
		
		if( !immediate)
		{
			delay = period;
		}
		
		timer.scheduleAtFixedRate( new MainThreadRunnableTimerTask( task), delay, period);
	    return timer;
	}
	
	public static void scheduleDelayedTask( Runnable task, long delay)
	{
		Handler handler = new Handler( Looper.getMainLooper());
		handler.postDelayed( task, delay);
	}
}
