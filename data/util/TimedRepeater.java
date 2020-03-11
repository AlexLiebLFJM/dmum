package be.alexandreliebh.picacademy.data.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedRepeater {

	private ScheduledExecutorService scheduler;

	public TimedRepeater(Runnable r, long initDelay, long period) {
		scheduler = Executors.newSingleThreadScheduledExecutor();

		int initialDelay = 4;
		int periodicDelay = 2;

		scheduler.scheduleAtFixedRate(r, initialDelay, periodicDelay, TimeUnit.SECONDS);
	}
	
	public void stop() {
		scheduler.shutdown();
	}
}
