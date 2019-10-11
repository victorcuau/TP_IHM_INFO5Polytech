package rangeslider;

import javax.swing.JSlider;

/**
 * 
 * On étend la classe JSlider pour y ajouter un deuxième levier permettant d'ajuster la borne supérieure de notre fourchette
 * Pour stocker cette borne, on peut utiliser une variable déjà présente dans le modèle : L'extent. Il est présent de base dans la classe BoundedRangeModel et n'est
 * pas utilisé de base par le JSlider. On s'en sert donc pour stocker les informations nécessaires à la position de notre seconde borne.
 */


public class RangeSlider extends JSlider {

	private static final long serialVersionUID = 1L;

	//Ainsi, on ajoute un nouveau constructeur, permettant de spécifier directement le min, le max, et les deux bornes de la fourchette
	public RangeSlider(int min, int max, int firstBound, int secondBound) {
		super.setMaximum(max);
		super.setMinimum(min);
		this.setFirstBound(firstBound);
		this.setSecondBound(secondBound);
	}

	//On peut aussi ne spécifier que le min et le max, les bornes de la fourchette seront donc ce min et ce max
	public RangeSlider(int min, int max) {
		super.setMaximum(max);
		super.setMinimum(min);
		this.setFirstBound(min);
		this.setSecondBound(max);
	}

	//Renvoie la valeur de la borne inférieure
	public int getFirstBound() {
		return getValue();
	}

	//Renvoie la valeur de la borne supérieure
	public int getSecondBound() {
		return getMaximum() - getExtent();
	}

	//Set la valeur de la borne inférieure
	public void setFirstBound(int first) {
		super.setValue(first);
	}

	//Set la valeur de la borne supérieure
	public void setSecondBound(int second) {
		super.setExtent(getMaximum() - second);
	}

	@Override
	public void updateUI() {
		//On ne peut pas utiliser le updateUI de la classe mère (JSlider) car il utilise un UIManager qui lui fournie l'UI avec le bon look and feel.
		//Seulement on a pas trouvé comment ajouter notre UI personnalisée pour le rangeSlider dans ce Manager. On le fourni donc directement en overridant la fonction.
		setUI(new RangeSliderUI(this));
		updateLabelUIs();
	}
	
}
