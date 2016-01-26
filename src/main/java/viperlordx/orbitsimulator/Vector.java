package viperlordx.orbitsimulator;

public class Vector {
	private double x;
	private double y;
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vector(Location p1, Location p2) {
		this.x = p2.x - p1.x;
		this.y = p2.y - p1.y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public Vector setX(double x) {
		this.x = x;
		return this;
	}
	public Vector setY(double y) {
		this.y = y;
		return this;
	}
	public Vector clone() {
		return new Vector(x, y);
	}
	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	public Vector normalize() {
		double length = length();
		x /= length;
		y /= length;
		return this;
	}
	public Vector multiply(double x, double y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	public Vector divide(double x, double y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	public Vector multiply(Vector v) {
		this.x *= v.x;
		this.y *= v.y;
		return this;
	}
	public Vector add(Vector v) {
		this.y += v.y;
		this.x += v.x;
		return this;
	}
	public Vector add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	public Vector subtract(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	public Vector subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	public Vector setLength(double length) {
		this.normalize().multiply(length, length);
		return this;
	}
	public void addTo(Location p) {
		p.addLocation(x, y);
	}
	public Vector invert() {
		x *= -1;
		y *= -1;
		return this;
	}
	public Vector round() {
		x = Math.round(x);
		y = Math.round(y);
		return this;
	}
}