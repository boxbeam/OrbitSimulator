package viperlordx.orbitsimulator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PlanetRemover extends JFrame {
	public PlanetRemover(Body planet) {
		super();
		JFrame frame = this;
		this.setResizable(false);
		this.setSize(400, 120 + this.getInsets().top);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Remove body?");
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		JButton yes = new JButton("Yes");
		yes.setSize(200, 100);
		yes.setLocation(0, 0);
		yes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				planet.remove();
				frame.dispose();
			}
		});
		this.add(yes);
		JButton no = new JButton("No");
		no.setSize(200, 100);
		no.setLocation(200, 0);
		no.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				frame.dispose();
			}
		});
		this.add(no);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Main.frame.setAlwaysOnTop(true);
				Main.frame.setAlwaysOnTop(false);
			}
		});
	}
}
