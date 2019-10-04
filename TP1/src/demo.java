import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class demo {
	
	public static void main(String[] args) {
		
		// Creation of the windows
		JFrame windows = new JFrame();
		
		windows.setTitle("Demo");
		windows.setSize(600, 400);
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.setResizable(true);
		windows.setLocationRelativeTo(null);
		
		// Creation of the container
		JPanel container = new JPanel();
		
		JLabel text = new JLabel("Test");
		container.add(text);
		
		JSlider slider = new JSlider(0,100);
		container.add(slider);
		
		RangeSlider rangeSlider = new RangeSlider(0,100);
		container.add(rangeSlider);
		
		windows.setContentPane(container);
		windows.setVisible(true);
	}
	
}
