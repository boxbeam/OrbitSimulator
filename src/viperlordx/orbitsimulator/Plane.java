package viperlordx.orbitsimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Plane extends JPanel {
	private boolean frozen = false;
	double scale = 1;
	public Object lock = new Object();
	private HashSet<Body> bodies = new HashSet<Body>();
	int ticksPerSecond = 30;
	private Location center = new Location(-500, -500);
	public void addBody(Body body) {
		bodies.add(body);
		body.setPlane(this);
		this.repaint();
	}
	public Plane() {
		super();
		this.setVisible(true);
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
	}
	@Override
	public void paintComponent(Graphics g) {
		synchronized (lock) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 5000, 5000);
			if (bodies != null) {
				for (Body body : bodies) {
					g.setColor(body.getColor());
					g.fillOval((int) (((body.getCenter().x)  - center.x) / scale) + 500, (int) (((body.getCenter().y) - center.y) / scale) + 500, (int) (body.getDiameter() / scale), (int) (body.getDiameter() / scale));
				}
			}
		}
	}
	public Set<Body> getBodies() {
		return bodies;
	}
	public void removeBody(Body body) {
		synchronized (lock) {
			bodies.remove(body);
			this.repaint();
		}
	}
	public void setCenter(Location location) {
		center = location;
	}
	public Location getCenter() {
		return center;
	}
	public Location getCenterOfMass() {
		double top_x = 0d, top_y = 0d;
	    double bottom = 0d;
	        for (Body a : bodies) {
	        top_x += a.getMass() * a.getLocation().x / scale;
	        top_y += a.getMass() * a.getLocation().y / scale;
	        bottom += a.getMass();
	    }
	    return new Location(top_x/bottom, top_y/bottom);
	}
	public void setFrozen(boolean frozen) {
		synchronized (lock) {
			this.frozen = frozen;
			for (Body body : bodies) {
				body.setStatic(frozen);
			}
		}
	}
	public boolean isFrozen() {
		return frozen;
	}
	public Body getBodyAt(Location location) {
		System.out.println(location.x);
		synchronized (lock) {
			for (Body body : bodies) {
				Location loc = new Location(((body.getCenter().x - center.x + (body.getDiameter() / 2)) / scale) + 500, ((body.getCenter().y - center.y + (body.getDiameter() / 2)) / scale) + 500);
				System.out.println(loc.x);
				double radius = body.getDiameter();
				if (loc.distanceTo(location) <= radius) {
					return body;
				}
			}
		}
		return null;
	}
	public Location getPlaneLocation(Location point) {
		Location center = getCenter().clone();
		double x = ((point.dx + center.x + 500) * scale);
		double y = ((point.dy + center.y + 500) * scale);
		return new Location(x, y);
	}
	public Location getUnscaledPlaneLocation(Location point) {
		Location center = getCenter().clone();
		double x = (point.dx + center.x) + (500 * scale);
		double y = (point.dy + center.y) + (500 * scale);
		return new Location(x, y);
	}
	public Location getComponentLocation(Location location) {
		double x = location.dx / scale;
		double y = location.dy / scale;
		return new Location(x, y);
	}
}