package homefinder;

public class Home {
	
	public int nbRooms;
	public int price;
	public int posX;
	public int posY;
	
	public Home(int nbRooms, int price, int posX, int posY) {
		this.nbRooms = nbRooms;
		this.price = price;
		this.posX = posX;
		this.posY = posY;
	}
	
	public Home() {
		this.nbRooms = (int)Math.random() * 10;
		this.price = ((int)Math.random() * 300000 + 10000)%100;
		this.posX = (int)Math.random() * 100;
		this.posY = (int)Math.random() * 100;
	}

}
