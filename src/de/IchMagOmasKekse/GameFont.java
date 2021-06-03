package de.IchMagOmasKekse;

import java.awt.Font;

public enum GameFont {
	
	DEFAULT(new Font("Arial", 0, 14));
	
	Font font;
	
	GameFont(Font font) {
		this.font = font;
	}
	
	public Font getFont() {
		return font;
	}
	
	
}
