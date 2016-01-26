package viperlordx.orbitsimulator;

import java.awt.Point;

public class Location {
	public int x;
	public int y;
	public double dx;
	public double dy;
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
		dx = x;
		dy = y;
	}
	public Location(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
		dx = x;
		dy = y;
	}
	public Location(Point point) {
		this.x = point.x;
		this.y = point.y;
		this.dx = point.x;
		this.dy = point.y;
	}
	public Location setLocation(double x, double y) {
		dx = x;
		dy = y;
		this.x = (int) Math.round(dx);
		this.y = (int) Math.round(dy);
		return this;
	}
	public Location addLocation(double x, double y) {
		dx += x;
		dy += y;
		this.x = (int) Math.round(dx);
		this.y = (int) Math.round(dy);
		return this;
	}
	public double distanceTo(Location other) {
		double disx = other.dx - this.dx;
		double disy = other.dy - this.dy;
		return Math.sqrt(Math.pow(disx, 2) + Math.pow(disy, 2));
	}
	public Location clone() {
		return new Location(dx, dy);
	}
	public Location subtractLocation(double x, double y) {
		dx -= x;
		dy -= y;
		this.x = (int) Math.round(dx);
		this.y = (int) Math.round(dy);
		return this;
	}
}
