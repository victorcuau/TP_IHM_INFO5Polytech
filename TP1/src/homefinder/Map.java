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
	public int					minRoom;
	public int					maxRoom;

	public Map(int width, int height, int minPrice, int maxPrice, int minRoom, int maxRoom) {
		this.createHomeList(NB_HOME);
		this.width = width;
		this.height = height;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.minRoom = minRoom;
		this.maxRoom = maxRoom;
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(width, height));
	}

	public void createHomeList(int nbHome) {
		for (int i = 0; i < nbHome; i++) {
			Home h = new Home();
			homeList.add(h);
//			System.out.println("X" + h.posX);
//			System.out.println("Y" + h.posY);
//			System.out.println("€" + h.price);
//			System.out.println("#" + h.nbRooms);
//			System.out.println(" ");
		}
		System.out.println(homeList.size() + " homes created on the map.");
	}

	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < this.homeList.size(); i++) {
			Home currentHome = homeList.get(i);
			if (currentHome.price >= this.minPrice && currentHome.price <= this.maxPrice) {
				if (currentHome.nbRooms >= this.minRoom && currentHome.nbRooms <= this.maxRoom) {
					g.setColor(Color.RED);
					g.fillOval(currentHome.posX, currentHome.posY, DIAMETER, DIAMETER);
				}
			}
		}
	}
	
	public void changeDisplay(int minPrice, int maxPrice, int minRoom, int maxRoom) {
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.minRoom = minRoom;
		this.maxRoom = maxRoom;
		this.removeAll();
		this.repaint();
	}

}
