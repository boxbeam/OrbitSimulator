package viperlordx.orbitsimulator;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;

import viperlordx.orbitsimulator.scheduler.Scheduler;

public class Main {
	private static boolean camera = false;
	public static Plane plane;
	public static JFrame frame;
	public static Scheduler scheduler;
	public static void main(String[] args) {
		scheduler = new Scheduler();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000 + frame.getInsets().top, 1000);
		frame.setLocationRelativeTo(null);
		plane = new Plane();
		frame.add(plane);
		plane.setVisible(true);
		frame.setLayout(null);
		plane.setBounds(frame.getInsets().top, 0, 1000 - frame.getInsets().top, 1000);
		plane.setBackground(Color.WHITE);
		Body planet = new Body(40.0, new Location(300, 300), new Vector(3, -2), Color.GREEN, scheduler);
		Body star = new Body(200.0, new Location(0, 0), new Vector(-1, 1), Color.YELLOW, scheduler);
		plane.addBody(star);
		plane.addBody(planet);
		frame.setVisible(true);
		frame.setTitle("Orbit");
		Location location = new Location(star.getLocation().x, star.getLocation().y);
		plane.setCenter(location);
		frame.setResizable(false);
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					plane.setFrozen(!plane.isFrozen());
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					camera = !camera;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		MouseAdapter adapter = new MouseAdapter() {
			Location start;
			int button;
			@Override
			public void mousePressed(MouseEvent e) {
				button = e.getButton();
				if (plane.isFrozen() && frame.isFocused() && e.getButton() == 1) {
					Body body = plane.getBodyAt(new Location(e.getPoint()));
					if (body != null) {
						new PlanetRemover(body);
					} else {
						new PlanetMaker(new Location(e.getPoint()));
					}
				} else {
					if (e.getButton() == 3 || e.getButton() == 2) {
						start = new Location(e.getPoint());
					}
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if ((button == 3 || button == 2) && (!camera || plane.isFrozen())) {
					Location end = new Location(Math.floor(e.getX()), Math.floor(e.getY()));
					Location difference = end.subtractLocation(start.x, start.y);
					plane.getCenter().subtractLocation(difference.x * plane.scale, difference.y * plane.scale);
					start = new Location(e.getPoint());
				}
			}
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				plane.scale += ((double) e.getWheelRotation()) / 5;
			}
		};
		frame.addMouseWheelListener(adapter);
		frame.addMouseListener(adapter);
		frame.addMouseMotionListener(adapter);
		scheduler.scheduleTask(new Runnable() {
			@Override
			public void run() {
				if (camera && !plane.isFrozen()) {
					Location location = plane.getCenterOfMass();
					plane.setCenter(location);
				}
			}
		}, 1000 / Body.TICKS_PER_SECOND, true, false);
	}
}