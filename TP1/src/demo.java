import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import homefinder.Map;
import rangeslider.RangeSlider;

public class demo {

	public static void main(String[] args) {

		// Creation of the windows
		JFrame windows = new JFrame();

		windows.setTitle("Demo");
		windows.setSize(600, 400);
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.setResizable(true);
		windows.setLocationRelativeTo(null);

		// Creation of the container of the range slider
		JPanel container = new JPanel();

		RangeSlider rangeSlider = new RangeSlider(0, 100);

		JLabel rangeSliderLabel1 = new JLabel();
		JLabel rangeSliderValue1 = new JLabel();
		JLabel rangeSliderLabel2 = new JLabel();
		JLabel rangeSliderValue2 = new JLabel();

		// Permet de modifier le texte des labels lors de modifications du rangeSlider
		rangeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider slider = (RangeSlider) e.getSource();
				rangeSliderValue1.setText(String.valueOf(slider.getFirstBound()));
				rangeSliderValue2.setText(String.valueOf(slider.getSecondBound()));
			}
		});

		rangeSliderLabel1.setText("Lower value:");
		rangeSliderLabel2.setText("Upper value:");
		rangeSlider.setFirstBound(20);
		rangeSlider.setSecondBound(50);

		container.add(rangeSliderLabel1);
		container.add(rangeSliderValue1);
		container.add(rangeSliderLabel2);
		container.add(rangeSliderValue2);
		container.add(rangeSlider);

		windows.setContentPane(container);
		windows.setVisible(true);
		
		
		JFrame windows2 = new JFrame();
		windows2.setTitle("Map");
		windows2.setSize(600, 400);
		windows2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows2.setResizable(true);
		windows2.setLocationRelativeTo(null);
		
		JPanel container2 = new JPanel();
		container2.add(new Map(windows2.getWidth()-10, windows2.getHeight()-10, Integer.parseInt(rangeSliderValue1.getText()), Integer.parseInt(rangeSliderValue2.getText())));
		
		windows2.setContentPane(container2);
		windows2.setVisible(true);

		
		
		
		
	}

}
