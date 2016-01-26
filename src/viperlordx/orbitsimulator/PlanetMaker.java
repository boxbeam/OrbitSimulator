package viperlordx.orbitsimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
public class PlanetMaker extends JFrame {
	public PlanetMaker(Location location) {
		super();
		JFrame frame = this;
		NumberFormat longFormat = NumberFormat.getIntegerInstance();
		NumberFormatter numberFormatter = new NumberFormatter(longFormat);
		numberFormatter.setValueClass(Long.class);
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setMinimum(1l);
		
		NumberFormat rgb = NumberFormat.getIntegerInstance();
		NumberFormatter rgbFormat = new NumberFormatter(rgb);
		rgbFormat.setValueClass(Integer.class);
		rgbFormat.setMaximum(255);
		rgbFormat.setMinimum(0);
		rgbFormat.setAllowsInvalid(false);
		
		NumberFormat vel = NumberFormat.getIntegerInstance();
		NumberFormatter velFormat = new NumberFormatter(vel);
		velFormat.setValueClass(Integer.class);
		velFormat.setMaximum(100);
		velFormat.setMinimum(-100);
		velFormat.setAllowsInvalid(false);
		
		this.setSize(new Dimension(500, 180));
		this.setResizable(false);
		this.setTitle("New body");
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		JFormattedTextField mass = new JFormattedTextField(numberFormatter);
		mass.setBounds(0, 0, 500, 30);
		mass.setToolTipText("Mass");
		mass.setVisible(true);
		this.add(mass);
		
		JFormattedTextField red = new JFormattedTextField(rgbFormat);
		red.setBounds(0, 30, 166, 30);
		red.setToolTipText("Red");
		red.setVisible(true);
		this.add(red);
		
		JFormattedTextField blue = new JFormattedTextField(rgbFormat);
		blue.setBounds(166, 30, 166, 30);
		blue.setToolTipText("Blue");
		blue.setVisible(true);
		this.add(blue);
		
		JFormattedTextField green = new JFormattedTextField(rgbFormat);
		green.setBounds(332, 30, 166, 30);
		green.setToolTipText("Green");
		green.setVisible(true);
		this.add(green);
		
		JFormattedTextField xvel = new JFormattedTextField(velFormat);
		xvel.setBounds(0, 60, 250, 30);
		xvel.setToolTipText("X Velocity");
		xvel.setVisible(true);
		this.add(xvel);
		
		JFormattedTextField yvel = new JFormattedTextField(velFormat);
		yvel.setBounds(250, 60, 250, 30);
		yvel.setToolTipText("Y Velocity");
		yvel.setVisible(true);
		this.add(yvel);
		
		JButton submit = new JButton("Create");
		submit.setToolTipText("Create body");
		submit.setBounds(0, 90, 500, 30);
		submit.setVisible(true);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					double bodyMass = Double.parseDouble(toNumberString(mass.getText()));
					int r = Integer.parseInt(toNumberString(red.getText()));
					int g = Integer.parseInt(toNumberString(green.getText()));
					int b = Integer.parseInt(toNumberString(blue.getText()));
					Color color = new Color(r, g, b);
					int vx = Integer.parseInt(toNumberString(xvel.getText()));
					int vy = Integer.parseInt(toNumberString(yvel.getText()));
					Vector velocity = new Vector(vx, vy);
					Body body = new Body(bodyMass, Main.plane.getPlaneLocation(location), velocity, color, Main.scheduler);
					Main.plane.addBody(body);
					body.setStatic(Main.plane.isFrozen());
					frame.dispose();
				}
			}
		});
		this.add(submit);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Main.frame.setAlwaysOnTop(true);
				Main.frame.setAlwaysOnTop(false);
			}
		});
	}
	private String toNumberString(String string) {
		string = string.replaceAll(",", "");
		return string;
	}
}
