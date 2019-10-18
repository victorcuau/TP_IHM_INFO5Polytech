import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class demo {


	public static void main(String[] args) {

	// Creation of the windows
		JFrame windows = new JFrame();
		windows.setTitle("Paint");
		windows.setSize(1200, 800);
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.setResizable(true);
		windows.setLocationRelativeTo(null);

	// Creation of the containers
		JPanel container = new JPanel();
		
	// Creation of the paint
		Paint paint = new Paint("test");
		container.add(paint);
		
	// Display the elements in the windows
		windows.setContentPane(container);
		windows.setVisible(true);
	}

}
