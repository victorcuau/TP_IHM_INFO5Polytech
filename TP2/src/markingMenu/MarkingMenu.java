package markingMenu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import demo.Paint;

/**
 * 
 * @author blow
 *	Vue of the Marking menu, use to get the input from the user, ask the controller what to do with them
 *	and finally draw them on the application.
 */
public class MarkingMenu implements MouseInputListener {
	private Controller cont;
	private Point p; 		//Center of the actual menu
	int radius = 80; 		//Radius of the menu
	JFrame parent;			//parent, so we can repaint
	boolean dragging;		//boolean set to true when we are dragging with right click
	
	public MarkingMenu(JFrame parent) {
		this.cont = new Controller(this);
		this.p = new Point();
		this.parent = parent; 
		this.dragging = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	//When we press a button on the mouse, we check if it is the right click, then we warn the controller
	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			cont.rightClickPressed(e.getPoint());
			this.dragging = true;
		}
		parent.repaint();
		
	}

	//when we release a button on the mouse
	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			cont.rightClickReleased(e.getPoint());
			this.dragging = false;
		}
		parent.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//If we are dragging the mouse with the right click still pressed, we warn the controller
	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.dragging) {
			cont.mouseDragged(e.getPoint());
		}
		parent.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// set and get the center of the actual menu
	public void setPoint(Point p) {
		this.p = p;
	}
	
	public Point getPoint() {
		return this.p;
	}
	
	// Calculate the zone of the cursor in the menu we are in right now.
	// returns 0 if we are out, or the index of the menu. (1, 2, etc)
	public int calculateZone(int x, int y) {
		String items[] = cont.getItems();
		double distanceToCenter = Math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y));
		if (distanceToCenter > radius) {
			return 0;
		} else {
			double angle = Math.toDegrees(Math.atan2(p.y - p.y, p.x - (p.x + radius)) - Math.atan2(p.y - y, p.x - x));
			int area = (int) ((angle * items.length) / 360) + 1;
			return area;
		}
	}
	
	public void setColor(Color c) {
		((Paint) this.parent).setColor(c);
	}
	
	public void setTool(int t) {
		((Paint) this.parent).setTool(t);
	}
	
	//Draw the marking menu based on the items of the menu we are in
	public void draw(Graphics g2) {
		String items[] = cont.getItems();
		if(items != null) {
			
			//We set a white background so we can read the menu
			g2.setColor(Color.WHITE);
			g2.fillOval(p.x - radius, p.y - radius, radius * 2, radius * 2);
			
			Graphics2D g2d = (Graphics2D) g2;
			g2d.setStroke(new BasicStroke(2));

			//We draw each item 
			for (int i = 1; i <= items.length; i++) {
				double angle = Math.toRadians((360 / items.length) * i);
				double angleText = angle + (Math.toRadians(360 / items.length)/2);
				int xi = (int) (radius * Math.cos(angle) + p.x);
				int yi = (int) (radius * Math.sin(angle) + p.y);
				int xt = (int) (60 * Math.cos(angleText) + p.x);
				int yt = (int) (60 * Math.sin(angleText) + p.y);
				
				g2.setColor(new Color(19, 163, 237));
				g2.drawLine(p.x, p.y, xi, yi);
				g2.setColor(Color.BLACK);
				g2d.drawString(items[i - 1], xt - (items[i - 1].length() * g2.getFont().getSize()) / 4,
						yt + g2.getFont().getSize() / 4);
				
			}
			g2d.setStroke(new BasicStroke(1));
		}
	}

}
