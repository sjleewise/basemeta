package kr.wise.scheduler;

import org.quartz.impl.QuartzServer;

public class DAScheduler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args == null || "".equals(args [0])) {
			System.err.println("Parameter is not found.");
		} else {
			if("START".equals(args [0])) {
				try {
					QuartzServer.main(args);
				} catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}
			if("SHUTDOWN".equals(args [0])) {
				try {
					QuartzSchedulerHelper.shutdown();
				} catch(Exception e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Parameter is invalid.");
			}
		}

	}

}
