package pi.password.service.util;

public class ThreadUtil {
	
	public static void createBackgroundJob(Runnable r) {
		new Thread(r).start();
	}

}
