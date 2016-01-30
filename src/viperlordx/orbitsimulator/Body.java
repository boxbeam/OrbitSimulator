package viperlordx.orbitsimulator;

import java.awt.Color;

import viperlordx.orbitsimulator.scheduler.Scheduler;

public class Body {
	private double mass;
	private Location location;
	private Vector velocity;
	private Color color;
	private Plane plane;
	private Scheduler scheduler;
	int task;
	private boolean frozen;
	public static int ticksPerSecond = 40;
	public Body(double mass, Location location, Vector velocity, Color color, Scheduler scheduler) {
		this.mass = mass;
		this.location = location;
		this.velocity = velocity;
		this.color = color;
		this.scheduler = scheduler;
		task = scheduler.scheduleTask(new Runnable() {
			@Override
			public void run() {
				moveTick();
			}
		}, 1000 / ticksPerSecond, true, false);
	}
	private synchronized void moveTick() {
		if (!frozen) {
			synchronized (plane.lock) {
				for (Body body : plane.getBodies()) {
					if (body != this && body.getMass() <= mass && body.getCenter().subtractLocation(body.getDiameter() / 2, body.getDiameter() / 2).distanceTo(this.getCenter()) < this.getDiameter()) {
						Vector diff = body.getVelocity();
						diff.subtract(velocity).multiply(body.getMass() / mass, body.getMass() / mass);
						velocity.add(diff);
						this.mass += body.getMass();
						body.remove();
						break;
					}
					if (body != this) {
						double distance = location.distanceTo(body.getLocation());
						distance /= 1;
						Vector vector = new Vector(location, body.getCenter().addLocation(body.getDiameter() / 2, body.getDiameter() / 2));
						vector.divide(Math.pow(distance, 2), Math.pow(distance, 2));
						vector.multiply(body.getMass() / 10, body.getMass() / 10);
						velocity.add(vector);
					}
				}
			}
			velocity.addTo(location);
		}
		plane.repaint();
	}
	public Color getColor() {
		return color;
	}
	public double getMass() {
		return mass;
	}
	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	public Plane getPlane() {
		return plane;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
	}
	public Vector getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector vector) {
		velocity = vector;
	}
	public Location getCenter() {
		Location center = new Location(location.x - (getDiameter()), location.y - (getDiameter()));
		return center;
	}
	public Location getScaledCenter() {
		Location center = new Location(location.x - (getDiameter() * plane.scale), location.y - (getDiameter() * plane.scale));
		return center;
	}
	public int getDiameter() {
		double r = getMass() / Math.PI * 100;
		r = Math.sqrt(r);
		int radius = (int) Math.ceil(r);
		return radius;
	}
	public double getExactDiameter() {
		double r = getMass() / Math.PI * 100;
		r = Math.sqrt(r);
		return r;
	}
	public void setStatic(boolean isStatic) {
		this.frozen = isStatic;
	}
	public boolean isStatic() {
		return frozen;
	}
	public void remove() {
		scheduler.cancelTask(task);
		plane.removeBody(this);
	}
	public Location getComponentLocation() {
		Location point = plane.getCenter().clone();
		double cx = point.dx - 500;
		double cy = point.dy - 500;
		double x = location.dx / plane.scale;
		double y = location.dy / plane.scale;
		x -= cx + (getDiameter() / 2 / plane.scale);
		y -= cy + (getDiameter() / 2 / plane.scale);
		return new Location(x, y);
	}
}