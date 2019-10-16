package homefinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel {

	static final int		NB_HOME		= 500;
	static final int		DIAMETER	= 10;

	public Vector<Home>	homeList	= new Vector<Home>();
	public int					width;
	public int					height;

	public int					minPrice;
	public int					maxPrice;
	public int					minRoom;
	public int					maxRoom;
	
	private Image backgroundImage;

	public Map(int width, int height, int minPrice, int maxPrice, int minRoom, int maxRoom, int defaultMinPrice, int defaultMaxPrice, int defaultMinRoom, int defaultMaxRoom) {
		this.createHomeList(NB_HOME, width, height, minPrice, maxPrice, minRoom, maxRoom);
		this.width = width;
		this.height = height;
		this.minPrice = defaultMinPrice;
		this.maxPrice = defaultMaxPrice;
		this.minRoom = defaultMinRoom;
		this.maxRoom = defaultMaxRoom;
//		this.setBackground(Color.WHITE);
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(width, height));
	}

	public void createHomeList(int nbHome, int xMax, int yMax, int minPrice, int maxPrice, int minRoom, int maxRoom) {
		for (int i = 0; i < nbHome; i++) {
			Home h = new Home(xMax, yMax, minRoom, maxRoom, minPrice, maxPrice);
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
					g.setColor(new Color(19, 163, 237));
					g.fillRect(currentHome.posX, currentHome.posY, DIAMETER, DIAMETER);
					Polygon triangle = new Polygon(new int[] { currentHome.posX, currentHome.posX + DIAMETER, (int)((currentHome.posX + DIAMETER/2)) }, 
																				 new int[] { currentHome.posY, currentHome.posY, currentHome.posY - DIAMETER/2 }, 3);
					g.setColor(Color.RED);
					g.fillPolygon(triangle);
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
	
	public void setImage(Image image) {
    this.backgroundImage = image;
    repaint();
	}
	
	public void setImage(String path) throws IOException {
    try {
        this.backgroundImage = ImageIO.read(new File(path));
        repaint();
    } 
    catch (IOException e) {
        throw new IOException(path+" introuvable", e);
    }
	}
	
	public void paintComponent(Graphics g){
    if(backgroundImage!=null){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
	}

}
