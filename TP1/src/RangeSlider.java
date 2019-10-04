import javax.swing.JComponent;

public class RangeSlider extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int min;
	public int max;
	
	
	public RangeSlider(int min, int max) {
		this.min = min;
		this.max = max;
	}

}
