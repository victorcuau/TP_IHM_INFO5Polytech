package markingMenu;

import java.awt.Color;
import java.awt.Point;

/**
 * 
 * @author blow
 *	Controls the vue and therefore the whole marking menu.
 *	It's here that the menu is stored and the decisions are made based on the input.
 */

public class Controller {
	
	private MarkingMenu vue;
	
	//First menu
	String itemsBaseMenu[] = { 
			"Couleurs",
			"Outils"};
	
	//Color menu
	String itemsColorMenu[] = {
			"<-Retour", 
			"Jaune", 
			"Rouge", 
			"Bleu", 
			"Vert", 
			"Noir" };
	
	//tool menu
		String itemsToolsMenu[] = {
			"Crayon",
			"Ligne",
			"Rectangle", 
			"<-Retour",
			"TODO", 
			"Ellipse" };
	
	
	private int actualMenu; //Menu we are in right now, 0 if we are not in a menu
	private int lastZone;	//Last zone the cursor was in
	private int tool;
	private Color color;
	
	public Controller(MarkingMenu mm) {
		this.vue = mm;
		this.actualMenu = 0;
		this.lastZone = 0;
		this.color = Color.BLACK; //default color
	}
	
	
	//Return the list of items of the current menu
	public String[] getItems() {
		switch(actualMenu) {
		case 1:
			return itemsBaseMenu;
		case 2:
			return itemsColorMenu;
		case 3:
			return itemsToolsMenu;
		default:
			return null;
		}
	}
	
	//Action based on a right click pressed (not released)
	public void rightClickPressed(Point p) {
		if(actualMenu == 0) {
			vue.setPoint(p);
			actualMenu = 1;
		}
	}
	
	//Action based on the release of the right click
	public void rightClickReleased(Point p) {
		if(actualMenu == 1) {
			actualMenu = 0;
		}
		actualMenu = 0;
	}
	
	//Action based on the drag of the mouse while the right click is still pressed
	public void mouseDragged(Point e) {
		//We get the actual zone
		int zone = vue.calculateZone(e.x, e.y);
		
		//And change the menu state based on the result and the menu we are in right now
		switch(actualMenu) {
		case 1:
			//base menu
			if(zone == 0) {
				switch(lastZone) {
				case 1:
					//color menu
					actualMenu = 2;
					vue.setPoint(e);
					break;
				case 2:
					//tool menu
					actualMenu = 3;
					vue.setPoint(e);
					break;
				}
			}else {
				lastZone = zone;
			}
			break;
		case 2:
			//color menu
			if(zone == 0) {
				switch(lastZone) {
				case 1:
					
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
					//we go back to the base menu
					vue.setPoint(e);
					actualMenu = 1;
					break;
				case 6:
					
					break;
					
				}
			}else {
				lastZone = zone;
			}
			break;
		case 3:
			//tool menu
			if(zone == 0) {
				switch(lastZone) {
				case 1:
					
					break;
				case 2:
					//we go back to the base menu
					vue.setPoint(e);
					actualMenu = 1;
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
					
					break;
				case 6:
					
					break;
					
				}
			}else {
				lastZone = zone;
			}
			break;				
		}
		
	}
	
	
}
















