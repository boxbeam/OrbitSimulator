package viperlordx.orbitsimulator.scheduler;

import java.util.HashSet;
import java.util.Iterator;

public class Scheduler {
	private volatile Thread thread;
	private volatile boolean started = false;
	private final Object tasksLock = new Object();
	private volatile HashSet<Pair<Task, Pair<Boolean, Boolean>>> tasks = new HashSet<Pair<Task, Pair<Boolean, Boolean>>>();
	public Scheduler() {
		thread = new Thread(){
			@Override
			public void run() {
				while (true) {
					synchronized (tasksLock) {
						Iterator<Pair<Task, Pair<Boolean, Boolean>>> iterator = tasks.iterator();
						while (iterator.hasNext()) {
							Pair<Task, Pair<Boolean, Boolean>> object = iterator.next();
							Task task = object.getFirst();
							Pair<Boolean, Boolean> pair = object.getSecond();
							if (task.getCooldown().isOver()) {
								boolean repeat = pair.getFirst();
								boolean async = pair.getSecond();
								task.run(async);
								if (!repeat) {
									iterator.remove();
								}
							} else {
								if (task.getCooldown().isStopped()) {
									iterator.remove();
								}
							}
						}
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	public int scheduleTask(Runnable runnable, int delay, boolean repeat, boolean async) {
		synchronized (tasksLock) {
			Task task = new Task(delay, runnable);
			Pair<Boolean, Boolean> pair = new Pair<Boolean, Boolean>();
			pair.setFirst(repeat);
			pair.setSecond(async);
			Pair<Task, Pair<Boolean, Boolean>> finalpair = new Pair<Task, Pair<Boolean, Boolean>>();
			finalpair.setFirst(task);
			finalpair.setSecond(pair);
			tasks.add(finalpair);
			start();
			return task.getId();
		}
	}
	public void cancelTask(int id) {
		synchronized (tasksLock) {
			Iterator<Pair<Task, Pair<Boolean, Boolean>>> iterator = tasks.iterator();
			while (iterator.hasNext()) {
				Pair<Task, Pair<Boolean, Boolean>> object = iterator.next();
				Task task = object.getFirst();
				if (task.getId() == id) {
					task.getCooldown().stop();
					break;
				}
			}
		}
	}
	private void start() {
		if (!started) {
			started = true;
			thread.start();
		}
	}
}