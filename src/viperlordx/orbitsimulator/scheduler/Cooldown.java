package viperlordx.orbitsimulator.scheduler;

public class Cooldown {
	private int cooldown;
	private long start = 0;
	private boolean stopped = false;
	public Cooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	public void start() {
		stopped = false;
		start = System.currentTimeMillis();
	}
	public boolean isOver() {
		if (!stopped) {
			long end = System.currentTimeMillis();
			return (end - start) > cooldown;
		} else {
			return false;
		}
	}
	public void stop() {
		stopped = true;
	}
	public boolean isStopped() {
		return stopped;
	}
}
