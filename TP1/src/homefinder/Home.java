package homefinder;

import java.util.Random;

public class Home {

	public int	nbRooms;
	public int	price;
	public int	posX;
	public int	posY;

//	public Home(int nbRooms, int price, int posX, int posY) {
//		this.nbRooms = nbRooms;
//		this.price = price;
//		this.posX = posX;
//		this.posY = posY;
//	}

//	public Home() {
//		Random randomGenerator = new Random();
//		this.nbRooms = randomGenerator.nextInt(10) + 1;
//		this.price = randomGenerator.nextInt(500000) + 1000;
//		this.posX = randomGenerator.nextInt(100) + 1;
//		this.posY = randomGenerator.nextInt(100) + 1;
//	}

	public Home(int xMax, int yMax, int minRoom, int maxRoom, int minPrice, int maxPrice) {
		Random randomGenerator = new Random();
		this.nbRooms = randomGenerator.nextInt(maxRoom) + minRoom;
		this.price = randomGenerator.nextInt(maxPrice) + minPrice;
		this.posX = randomGenerator.nextInt(xMax) + 1;
		this.posY = randomGenerator.nextInt(yMax) + 1;
	}

}
