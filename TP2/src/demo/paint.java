package demo;
//////////////////////////////////////////////////////////////////////////////
// file    : Paint.java
// content : basic painting app
//////////////////////////////////////////////////////////////////////////////

/* imports *****************************************************************/
import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import markingMenu.MarkingMenu;

/* paint *******************************************************************/

class Paint extends JFrame {

	class Tool extends AbstractAction implements MouseInputListener {
		
		private static final long serialVersionUID = 1L;
		Point o;
		Shape shape;

		public Tool(String name) {
			super(name);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("using tool " + this);
			panel.removeMouseListener(tool);
			panel.removeMouseMotionListener(tool);
			tool = this;
			panel.addMouseListener(tool);
			panel.addMouseMotionListener(tool);
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			o = e.getPoint();
		}

		public void mouseReleased(MouseEvent e) {
			shape = null;
		}

		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
		}
	}

	Tool tools[] = { new Tool("Pencil") {
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				Path2D.Double path = (Path2D.Double) shape;
				if (path == null) {
					path = new Path2D.Double();
					path.moveTo(o.getX(), o.getY());
					shapes.add(shape = path);
					shapes_colors.add(current_color);
				}
				path.lineTo(e.getX(), e.getY());
				panel.repaint();
			}
		}
	}, new Tool("Line") {
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				Line2D.Double line = (Line2D.Double) shape;
				if (line == null) {
					line = new Line2D.Double(o.getX(), o.getY(), 0, 0);
					shapes.add(shape = line);
					shapes_colors.add(current_color);
				}
				line.setLine(e.getX(), e.getY(), o.getX(), o.getY());
				panel.repaint();
			}
		}
	}, new Tool("Rectangle") {
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				Rectangle2D.Double rect = (Rectangle2D.Double) shape;
				if (rect == null) {
					rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
					shapes.add(shape = rect);
					shapes_colors.add(current_color);
				}
				rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
						abs(e.getY() - o.getY()));
				panel.repaint();
			}
		}
	}, new Tool("Ellipse") {
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				Ellipse2D.Double oval = (Ellipse2D.Double) shape;
				if (oval == null) {
					oval = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
					shapes.add(shape = oval);
					shapes_colors.add(current_color);
				}
				oval.setFrame(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
						abs(e.getY() - o.getY()));
				panel.repaint();
			}
		}
	} };
	
	private static final long serialVersionUID = 1L;
	Vector<Shape> shapes = new Vector<Shape>();
	Color current_color = Color.BLACK;
	Vector<Color> shapes_colors = new Vector<Color>();
	
	//We create our Marking menu
	private MarkingMenu markingMenu = new MarkingMenu(this);

	Tool tool;
	JPanel panel;

	public Paint(String title) {
		super(title);
		setTitle("Paint - Marking Menu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 800));
		add(new JToolBar() {
			{
				for (AbstractAction tool : tools) {
					add(tool);
				}
				JButton colorBox = new JButton("Color");
				colorBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						current_color = JColorChooser.showDialog(Paint.this, "Color box", current_color);
						if (current_color == null) {
							current_color = Color.BLACK;
						}
					}
				});
				add(colorBox);
			}
		}, BorderLayout.NORTH);
		add(panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth(), getHeight());

				int ind_color = 0;
				g2.setColor(Color.BLACK);
				for (Shape shape : shapes) {
					g2.setColor(shapes_colors.get(ind_color));
					g2.draw(shape);
					ind_color++;
				}
				
				//Don't forget to draw the marking menu !
				markingMenu.draw(g2);
				
			}
		});
		
		//Don't forget to add the markingMenu as a listener so we can interact with it
		panel.addMouseListener(markingMenu);
		panel.addMouseMotionListener(markingMenu);

		pack();
		setVisible(true);
	}
	
}