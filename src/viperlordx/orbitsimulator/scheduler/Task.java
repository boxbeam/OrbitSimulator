package viperlordx.orbitsimulator.scheduler;

public class Task {
	private Cooldown cooldown;
	private Runnable runnable;
	static int totalId = 0;
	int id;
	public Task(int delay, Runnable runnable) {
		totalId++;
		this.id = totalId;
		this.cooldown = new Cooldown(delay);
		this.runnable = runnable;
		cooldown.start();
	}
	public void run(boolean async) {
		if (async) {
			Thread thread = new Thread(runnable);
			thread.start();
		} else {
			runnable.run();
		}
		cooldown.start();
	}
	public Cooldown getCooldown() {
		return cooldown;
	}
	public int getId() {
		return id;
	}
}