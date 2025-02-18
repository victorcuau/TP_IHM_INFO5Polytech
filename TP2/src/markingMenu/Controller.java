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
			"Colors",
			"Tools" };
	
	//Color menu
	String itemsColorMenu[] = {
			"<-- Back",
			"Yellow",
			"Red",
			"Blue",
			"Green",
			"Black" };
	
	//tool menu
		String itemsToolsMenu[] = {
			"Pencil",
			"Line",
			"Rectangle",
			"<-- Back",
			"Circle",
			"Ellipse" };
	
	
	private int actualMenu; // Menu we are in right now, 0 if we are not in a menu
	private int lastZone;	// Last zone the cursor was in
	private int tool;
	private Color color;
	
	public Controller(MarkingMenu mm) {
		this.vue = mm;
		this.actualMenu = 0;
		this.lastZone = 0;
		this.color = Color.BLACK; // Default color
		this.tool = 1;
	}
	
	
	// Return the list of items of the current menu
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
	
	// Action based on a right click pressed (not released)
	public void rightClickPressed(Point p) {
		switch(actualMenu) {
		case 0:
			vue.setPoint(p);
			actualMenu = 1;
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
			
	}
	
	// Action based on the release of the right click
	// We touch at the application only when we are DONE using the marking menu,
	// and not at each movement
	public void rightClickReleased(Point p) {
		switch(actualMenu) {
		case 0:
			break;
		case 1:
			actualMenu = 0;
			break;
		case 2:
			vue.setColor(color);
			actualMenu = 0;
			break;
		case 3:
			vue.setTool(tool);
			actualMenu = 0;
			break;
		}
	}
	
	// Action based on the drag of the mouse while the right click is still pressed
	public void mouseDragged(Point e) {
		// Get the actual zone
		int zone = vue.calculateZone(e.x, e.y);
		
		// And change the menu state based on the result and the menu we are in right now
		switch(actualMenu) {
		case 1:
			// Base menu
			if(zone == 0) {
				switch(lastZone) {
				case 1:
					// Color menu
					actualMenu = 2;
					vue.setPoint(e);
					break;
				case 2:
					// Tool menu
					actualMenu = 3;
					vue.setPoint(e);
					break;
				}
			} else {
				lastZone = zone;
			}
			break;
			
		case 2:
			// Colors menu
			if(zone == 0 && lastZone == 5) {
				// We go back to the base menu
				vue.setPoint(e);
				actualMenu = 1;
			} else {
				if(zone != 0) {
					lastZone = zone;
					switch(zone) {
					case 1:
						color = Color.GREEN;
						break;
					case 2:
						color = Color.BLUE;
						break;
					case 3:
						color = Color.RED;
						break;
					case 4:
						color = Color.YELLOW;
						break;
					case 5:
						break;
					case 6:
						color = Color.BLACK;
						break;
					}
				}
			}
			break;
			
		case 3:
			// Tools menu
			if(zone == 0 && lastZone == 2) {
				// We go back to the base menu
				vue.setPoint(e);
				actualMenu = 1;
			} else {
				if(zone != 0) {
					lastZone = zone;
					tool = zone;
				}
			}
			break;				
		}
	}
	
}