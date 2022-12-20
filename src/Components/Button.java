package Components;

import java.awt.*;

public class Button extends ModelOfButtonWithGetColor {	// extends abstract class to force this class to override the abstract method
	
	private Color color;

    public Button(String x, Color color) {
        super(x);
        this.color = color;								// assign specified color to variable color of this class
        this.setPreferredSize(new Dimension(20,20));	// set preferred size
        this.setBackground(color);						// set background color to be specified color
        this.setOpaque(true);							// paint background color
    }

    public Button(Color color) {
        this.color = color;								// assign specified color to variable color of this class
        this.setPreferredSize(new Dimension(20,20));	// set preferred size
        this.setBackground(color);						// set background color to be specified color
        this.setOpaque(true);							// paint background color
    }

    public Color getColor() {									// this method is used to get color
        return color;
    }
}
