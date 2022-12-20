package Components;

import java.awt.Color;
import javax.swing.*;

public abstract class ModelOfButtonWithGetColor extends JButton {
	
	protected ModelOfButtonWithGetColor() {} //this method is automatically call super();
	
	protected ModelOfButtonWithGetColor(String x) {
		super(x);
	}
	
	protected abstract Color getColor();
}
