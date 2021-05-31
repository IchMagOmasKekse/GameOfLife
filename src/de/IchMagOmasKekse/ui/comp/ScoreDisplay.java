package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.GamePlay;

public class ScoreDisplay extends UICText {

    public ScoreDisplay(int x, int y, String fontName, int fontStyle, int fontSize) {
        super(x, y, fontName, fontStyle, fontSize);
    }

    public void update() {
        setText("Score: "+ GamePlay.score);
    }
}
