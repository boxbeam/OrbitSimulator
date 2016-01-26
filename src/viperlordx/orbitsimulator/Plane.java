package viperlordx.orbitsimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Plane extends JPanel {
	private boolean frozen = false;
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
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 5000, 5000);
		if (bodies != null) {
			for (Body body : bodies) {
				g.setColor(body.getColor());
				g.fillOval(body.getCenter().x - center.x, body.getCenter().y - center.y, body.getDiameter(),
						body.getDiameter());
			}
		}
	}
	public Set<Body> getBodies() {
		return bodies;
	}
	public void removeBody(Body body) {
		bodies.remove(body);
		this.repaint();
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
	        top_x += a.getMass() * a.getLocation().x;
	        top_y += a.getMass() * a.getLocation().y;
	        bottom += a.getMass();
	    }
	    return new Location(top_x/bottom, top_y/bottom);
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
		for (Body body : bodies) {
			body.setStatic(frozen);
		}
	}
	public boolean isFrozen() {
		return frozen;
	}
	public Body getBodyAt(Location location) {
		for (Body body : bodies) {
			int radius = body.getDiameter();
			if (body.getComponentLocation().distanceTo(location) < radius) {
				return body;
			}
		}
		return null;
	}
	public Location getPlaneLocation(Location point) {
		Location center = getCenter().clone();
		double x = center.dx + point.dx;
		double y = center.dy + point.dy;
		return new Location(x, y);
	}
}