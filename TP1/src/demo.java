import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import homefinder.Map;
import rangeslider.RangeSlider;

public class demo {

	static final int	MIN_PRICE					= 1000;
	static final int	MAX_PRICE					= 500000;
	static final int	DEFAULT_MIN_PRICE	= 50000;
	static final int	DEFAULT_MAX_PRICE	= 200000;
	static final int	MIN_ROOM					= 1;
	static final int	MAX_ROOM					= 10;
	static final int	DEFAULT_MIN_ROOM	= 3;
	static final int	DEFAULT_MAX_ROOM	= 6;

	public static void main(String[] args) {

	// Creation of the windows
		JFrame windows = new JFrame();
		windows.setTitle("Dynamic Home Finder");
		windows.setSize(1200, 800);
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.setResizable(true);
		windows.setLocationRelativeTo(null);

	// Creation of the containers
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());

		JPanel containerEast = new JPanel();
		containerEast.setLayout(new BoxLayout(containerEast, BoxLayout.PAGE_AXIS));
		container.add(containerEast, BorderLayout.EAST);

	// Creation of the Price RangeSlider
		RangeSlider rangeSliderPrice = new RangeSlider(MIN_PRICE, MAX_PRICE);

		JLabel titlePrice = new JLabel();

		JPanel priceLabelsContainer = new JPanel();
		priceLabelsContainer.setLayout(new BoxLayout(priceLabelsContainer, BoxLayout.LINE_AXIS));
		JLabel labelPrice1 = new JLabel();
		JLabel roomLabelSeparator = new JLabel();
		JLabel labelPrice2 = new JLabel();

		containerEast.add(titlePrice);
		containerEast.add(rangeSliderPrice);
		containerEast.add(priceLabelsContainer);
		priceLabelsContainer.add(labelPrice1);
		priceLabelsContainer.add(roomLabelSeparator);
		priceLabelsContainer.add(labelPrice2);

		containerEast.add(Box.createVerticalStrut(20)); // Add an empty space

	// Creation of the Room RangeSlider
		RangeSlider rangeSliderRoom = new RangeSlider(MIN_ROOM, MAX_ROOM);

		JLabel titleRoom = new JLabel();

		JPanel roomLabelsContainer = new JPanel();
		roomLabelsContainer.setLayout(new BoxLayout(roomLabelsContainer, BoxLayout.LINE_AXIS));
		JLabel labelRoom1 = new JLabel();
		JLabel priceLabelSeparator = new JLabel();
		JLabel labelRoom2 = new JLabel();
		
		containerEast.add(titleRoom);
		containerEast.add(rangeSliderRoom);
		containerEast.add(roomLabelsContainer);
		roomLabelsContainer.add(labelRoom1);
		roomLabelsContainer.add(priceLabelSeparator);
		roomLabelsContainer.add(labelRoom2);

	// Creation of the Map
		Map map = new Map(windows.getWidth() - 200, windows.getHeight() - 25, MIN_PRICE, MAX_PRICE, MIN_ROOM, MAX_ROOM, DEFAULT_MIN_PRICE, DEFAULT_MAX_PRICE, DEFAULT_MIN_ROOM, DEFAULT_MAX_ROOM);
		try {
			map.setImage("images/map.png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		container.add(map);

	// Listener to update data
		// Update when the room range slider has moved
		rangeSliderRoom.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider sliderRoom = (RangeSlider) e.getSource();
				// Update the text of the labels
				labelRoom1.setText(String.valueOf(sliderRoom.getFirstBound()));
				labelRoom2.setText(String.valueOf(sliderRoom.getSecondBound()));
				// Update the map
				map.changeDisplay(map.minPrice, map.maxPrice, sliderRoom.getFirstBound(), sliderRoom.getSecondBound());
			}
		});

		// Update when the price range slider has moved
		rangeSliderPrice.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				RangeSlider sliderPrice = (RangeSlider) e.getSource();
				// Update the text of the labels
				labelPrice1.setText(String.valueOf(sliderPrice.getFirstBound()));
				labelPrice2.setText(String.valueOf(sliderPrice.getSecondBound()));
				// Update the map
				map.changeDisplay(sliderPrice.getFirstBound(), sliderPrice.getSecondBound(), map.minRoom, map.maxRoom);
			}
		});
		
	// Initialisation of the values
		titlePrice.setText("PRICE (€)");
		rangeSliderPrice.setFirstBound(DEFAULT_MIN_PRICE);
		roomLabelSeparator.setText(" - ");
		rangeSliderPrice.setSecondBound(DEFAULT_MAX_PRICE);
		
		titleRoom.setText("Number of room(s)");
		rangeSliderRoom.setFirstBound(DEFAULT_MIN_ROOM);
		priceLabelSeparator.setText(" - ");
		rangeSliderRoom.setSecondBound(DEFAULT_MAX_ROOM);

	// Display the elements in the windows
		windows.setContentPane(container);
		windows.setVisible(true);
	}

}
