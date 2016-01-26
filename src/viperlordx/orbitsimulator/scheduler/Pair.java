package viperlordx.orbitsimulator.scheduler;

public class Pair<T1, T2> {
	private T1 value1;
	private T2 value2;
	public Pair() {
	}
	public void setFirst(T1 first) {
		value1 = first;
	}
	public void setSecond(T2 second) {
		value2 = second;
	}
	public T1 getFirst() {
		return value1;
	}
	public T2 getSecond() {
		return value2;
	}
}
