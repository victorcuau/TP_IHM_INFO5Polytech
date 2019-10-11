package homefinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

public class Map extends JPanel {

	static final int		NB_HOME		= 20;
	static final int		DIAMETER	= 5;

	public Vector<Home>	homeList	= new Vector<Home>();
	public int					width;
	public int					height;

	public int					minPrice;
	public int					maxPrice;

	public Map(int width, int height, int minPrice, int maxPrice) {
		this.createHomeList(NB_HOME);
		this.width = width;
		this.height = height;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(width, height));
	}

	public void createHomeList(int nbHome) {
		for (int i = 0; i < nbHome; i++) {
			Home h = new Home();
			homeList.add(h);
			System.out.println('X' + h.posX + 'Y' + h.posY + '€' + h.price + '#' + h.nbRooms);
		}
		System.out.println(homeList.size());
	}

	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < this.homeList.size(); i++) {
			Home currentHome = homeList.get(i);
			if (currentHome.price >= this.minPrice && currentHome.price <= this.maxPrice) {
				g.setColor(Color.RED);
				g.fillOval(currentHome.posX, currentHome.posY, DIAMETER, DIAMETER);
			}
		}
	}
	
	public void changeDisplay(int minPrice, int maxPrice) {
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.removeAll();
		this.repaint();
	}

}
