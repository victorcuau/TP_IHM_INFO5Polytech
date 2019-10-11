package rangeslider;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * 
 * On étend la classe BasicSliderUI pour y ajouter un deuxième levier permettant d'ajuster la borne supérieure de notre fourchette.
 * Les modifications ne sont pas très compliquées, il suffit de créer un nouveau rectangle qui représente la borne droite,
 * puis de modifier les fonctions existantes afin que les mises à jour lors des modifications soient bien prises en compte dans le model
 * ainsi que graphiquement. On rajoute aussi des contraintes liées au fonctionnement d'un range slider.
 * La machine à état se trouve donc ici dans le rangeTrackListener.
 */

public class RangeSliderUI extends BasicSliderUI {

	protected Rectangle NewthumbRect = null;
    
    private transient boolean isDraggingLeft;
    private transient boolean isDraggingRight;
    
    private int lastValue;
    
	public RangeSliderUI(JSlider b) {
		super(b);
	}
	
	
	//Renvoie une taille custom de borne
    @Override
    protected Dimension getThumbSize() {
        return new Dimension(16, 16);
    }
    
    //On ne souhaite pas que l'ancien code d'affichage des bornes soit utilisé, on utilise le notre.
    //(Sans cet ajout, on voit apparaitre en même temps 2 dessins l'un sur l'autre)
    @Override
    public void paintThumb(Graphics g) {
    }
	
	//Permet de tracer la borne de gauche
	public void paintThumbRight(Graphics g)  
	{     
		Rectangle borne = NewthumbRect;
        int w = borne.width;
        int h = borne.height;      
        
        // Permet de changer la forme de la borne, on essaie de se rapprocher d'un design minimaliste souvent retrouvé sur les sites web
        Graphics2D g2d = (Graphics2D) g.create();

        // Crée une borne en forme de cercle
        Shape thumbShape = new Ellipse2D.Double(0, 0, w - 1, h - 1);
        Shape shadow = new Ellipse2D.Double(1, 1, w -3, h -3);

        //Dessine la borne au bon endroit et rajoute de l'antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(borne.x, borne.y);

        //Interieur blanc
        g2d.setColor(Color.WHITE);
        g2d.fill(thumbShape);

        //Contour de la borne
        g2d.setColor(new Color(19,163,237));
        g2d.draw(thumbShape);
        //pour un double contour
        g2d.draw(shadow);
        
        // Dispose graphics.
        g2d.dispose();
    }
	
	//Permet de tracer la borne de droite
	public void paintThumbLeft(Graphics g)  
	{
		Rectangle borne = thumbRect;
        int w = borne.width;
        int h = borne.height;      
        
        // Permet de changer la forme de la borne, on essaie de se rapprocher d'un design minimaliste souvent retrouvé sur les sites web
        Graphics2D g2d = (Graphics2D) g.create();

        // Crée une borne en forme de cercle
        Shape thumbShape = new Ellipse2D.Double(0, 0, w - 1, h - 1);
        Shape shadow = new Ellipse2D.Double(1, 1, w -3, h -3);

        //Dessine la borne au bon endroit et rajoute de l'antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(borne.x, borne.y);

        //Interieur blanc
        g2d.setColor(Color.WHITE);
        g2d.fill(thumbShape);

        //Contour de la borne
        g2d.setColor(new Color(19,163,237));
        g2d.draw(thumbShape);
        //pour un double contour
        g2d.draw(shadow);
        
        // Dispose graphics.
        g2d.dispose();
    }
	

    public void installUI(JComponent c)   {
    	NewthumbRect = new Rectangle();
        super.installUI(c);
    }
    
    public void uninstallUI(JComponent c) {
    	NewthumbRect = null;
        super.uninstallUI(c);
    }
    
    //permet de tracer notre barre personnalisées
    public void paintTrack(Graphics g)  {

        Rectangle trackBounds = trackRect;

        int cy = (trackBounds.height / 2) -6;
        int cw = trackBounds.width;

        g.translate(trackBounds.x, trackBounds.y + cy);

        //Tracage du cadre du range slider, je choisi ici un rectangle
        g.setColor(new Color(140,140,140));
        g.fillRect(1, 4, cw-1, 4);
        
        g.translate(-trackBounds.x, -(trackBounds.y + cy));
        
        //dessin de la zone selectionnée
        this.paintSelectedTrack(g);

    }
    
    //permet de tracer en bleu la zone selectionnées par nos bornes.
    private void paintSelectedTrack(Graphics g) {
    	
    	Rectangle trackBounds = trackRect;
    	
    	//Calcul de la position des bornes
        int lowerX = thumbRect.x + (thumbRect.width / 2);
        int upperX = NewthumbRect.x + (NewthumbRect.width / 2);
        
        //Calcul de la position du rectangle
        int cy = (trackBounds.height / 2) - 2;

        //Sauvegarde de la position du curseur et de la couleur actuel
        Color oldColor = g.getColor();
        g.translate(trackBounds.x, trackBounds.y + cy);
        
        //On trace un trait permettant de remplir le rectangle entre les deux bornes
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(9));
        g.setColor(new Color(19,163,237));
        g.drawLine(lowerX - trackBounds.x, trackBounds.y+1, upperX - trackBounds.x, trackBounds.y+1);
        g2.setStroke(new BasicStroke(1));
        
        //Restoration de l'ancienne position du curseur ainsi que de l'ancienne couleur
        g.translate(-trackBounds.x, -(trackBounds.y + cy));
        g.setColor(oldColor);
    }
    
    //retrace tout le composant
    public void paint( Graphics g, JComponent c )   {
        super.paint(g, c);
        paintThumbLeft(g);
        paintThumbRight(g);
    }
    
    //permet de calculer la position de nos bornes
    @Override
    protected void calculateThumbLocation() {
    	//calcul la borne inférieure grace au code d'origine
    	super.calculateThumbLocation();
    	
    	//il nous reste donc à nous occuper de la borne supérieure
        if ( slider.getSnapToTicks() ) {
            int rightValue = ((RangeSlider) slider).getSecondBound();
            int snappedValue = rightValue;
            int majorTickSpacing = slider.getMajorTickSpacing();
            int minorTickSpacing = slider.getMinorTickSpacing();
            int tickSpacing = 0;

            if ( minorTickSpacing > 0 ) {
                tickSpacing = minorTickSpacing;
            }
            else if ( majorTickSpacing > 0 ) {
                tickSpacing = majorTickSpacing;
            }

            if ( tickSpacing != 0 ) {
                if ( (rightValue - slider.getMinimum()) % tickSpacing != 0 ) {
                    float temp = (float)(rightValue - slider.getMinimum()) / (float)tickSpacing;
                    int whichTick = Math.round( temp );

                    if (temp - (int)temp == .5 && rightValue < lastValue) {
                      whichTick --;
                    }
                    snappedValue = slider.getMinimum() + (whichTick * tickSpacing);
                }

                if( snappedValue != rightValue ) {
                    slider.setExtent( snappedValue - slider.getMinimum() );
                }
            }
        }

        if ( slider.getOrientation() == JSlider.HORIZONTAL ) {
            int rightPosition = xPositionForValue(((RangeSlider) slider).getSecondBound());

            NewthumbRect.x = rightPosition - (NewthumbRect.width / 2);
            NewthumbRect.y = trackRect.y;
        }
        else {
            int rightPosition = yPositionForValue(slider.getValue());

            NewthumbRect.x = trackRect.x;
            NewthumbRect.y = rightPosition - (NewthumbRect.height / 2);
        }
    }
    
    protected void calculateThumbSize()
    {
    	super.calculateThumbSize();
    	NewthumbRect.setSize(thumbRect.width, thumbRect.height);
    }
    
    
    public void setRightThumbLocation(int x, int y)  {
        Rectangle rightUnion = new Rectangle(); 
        rightUnion.setBounds( NewthumbRect );

        NewthumbRect.setLocation( x, y );

        SwingUtilities.computeUnion( NewthumbRect.x, NewthumbRect.y, NewthumbRect.width, NewthumbRect.height, rightUnion );
        slider.repaint( rightUnion.x, rightUnion.y, rightUnion.width, rightUnion.height );
    }
    

    @Override
    protected TrackListener createTrackListener(JSlider slider) {
        return new RangeTrackListener();
    }

    @Override
    protected ChangeListener createChangeListener(JSlider slider) {
        return new ChangeThumbListener();
    }
    
    public class ChangeThumbListener implements ChangeListener {
        public void stateChanged(ChangeEvent arg0) {
            if (!isDraggingLeft && !isDraggingRight) {
                calculateThumbLocation();
                slider.repaint();
            }
        }
    }
    
    public class RangeTrackListener extends TrackListener {
        
    	@Override
        public void mouseReleased(MouseEvent e) {
    		isDraggingLeft = false;
    		isDraggingRight	 = false;
            slider.setValueIsAdjusting(false);
            super.mouseReleased(e);
        }
    	
    	@Override
        public void mousePressed(MouseEvent e) {
            if (!slider.isEnabled()) {
                return;
            }

            currentMouseX = e.getX();
            currentMouseY = e.getY();

            if (slider.isRequestFocusEnabled()) {
                slider.requestFocus();
            }
            
            
            boolean leftPressed = false;
            boolean rightPressed = false;
            if (NewthumbRect.contains(currentMouseX, currentMouseY)) {
                   rightPressed = true;
            } else if (thumbRect.contains(currentMouseX, currentMouseY)) {
                  leftPressed = true;
            }

            if (leftPressed) {
                switch (slider.getOrientation()) {
                case JSlider.VERTICAL:
                    offset = currentMouseY - thumbRect.y;
                    break;
                case JSlider.HORIZONTAL:
                    offset = currentMouseX - thumbRect.x;
                    break;
                }
                isDraggingLeft = true;
                return;
            }
            isDraggingLeft = false;
            
            
            if (rightPressed) {
                switch (slider.getOrientation()) {
                case JSlider.VERTICAL:
                    offset = currentMouseY - NewthumbRect.y;
                    break;
                case JSlider.HORIZONTAL:
                    offset = currentMouseX - NewthumbRect.x;
                    break;
                }
                isDraggingRight = true;
                return;
            }
            isDraggingRight = false;
        }
    	
    	@Override
        public void mouseDragged(MouseEvent e) {
            if (!slider.isEnabled()) {
                return;
            }

            currentMouseX = e.getX();
            currentMouseY = e.getY();

            if (isDraggingLeft) {
                slider.setValueIsAdjusting(true);
                mouseDraggedLeftThumb();
                
            } else if (isDraggingRight) {
                slider.setValueIsAdjusting(true);
                mouseDraggedRightThumb();
            }
        }
    	
    	private void mouseDraggedLeftThumb() {
            int thumbMiddle = 0;
           
            int halfThumbWidth = thumbRect.width / 2;
            int thumbLeft = currentMouseX - offset;
            int trackLeft = trackRect.x;
            int trackRight = trackRect.x + (trackRect.width - 1);
            int hMax = xPositionForValue(((RangeSlider) slider).getSecondBound());
            
            if (drawInverted()) {
                trackLeft = hMax;
            } else {
                trackRight = hMax;
            }
            thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
            thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

            
            
            setThumbLocation(thumbLeft, thumbRect.y);

            thumbMiddle = thumbLeft + halfThumbWidth;
            
            ((RangeSlider) slider).setFirstBound(valueForXPosition(thumbMiddle));
            
        }


        private void mouseDraggedRightThumb() {
            int thumbMiddle = 0;
            
            int halfThumbWidth = thumbRect.width / 2;
            int thumbLeft = currentMouseX - offset;
            int trackLeft = trackRect.x;
            int trackRight = trackRect.x + (trackRect.width - 1);
            int hMin = xPositionForValue(((RangeSlider) slider).getFirstBound());

            if (drawInverted()) {
                trackRight = hMin;
            } else {
                trackLeft = hMin;
            }
            thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
            thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

            setRightThumbLocation(thumbLeft, thumbRect.y);
            
            thumbMiddle = thumbLeft + halfThumbWidth;
            ((RangeSlider) slider).setSecondBound(valueForXPosition(thumbMiddle));
        }
    	
    	@Override
        public boolean shouldScroll(int direction) {
            return false;
        }
    }
    
    
    
}
