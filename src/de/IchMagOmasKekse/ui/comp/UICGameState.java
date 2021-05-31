package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.Chat;
import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.ui.UI;
import de.IchMagOmasKekse.ui.UIC;
import de.IchMagOmasKekse.ui.UICNames;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class UICGameState extends UIC {

    private BufferedImage play_img, pause_img, disabled_play_img, disabled_pause_img;

    public UICGameState(int x, int y, int width, int height) {
        super(UICNames.UIC_GAMESTATE.getSystemName(), x, y, width, height);
        defineColorsToDefault();
        font = new Font("Arial", 1, 1);
        c_infill = Color.WHITE;
        c_border = Color.GRAY;

        BufferedImage img = Simulation.textureAtlas.getTexture("play_pause");
        play_img = img.getSubimage(0, 0, 32, 64);
        pause_img = img.getSubimage(33, 0, 31, 64);

        img = Simulation.textureAtlas.getTexture("disabled_play_pause");
        disabled_play_img = img.getSubimage(0, 0, 32, 64);
        disabled_pause_img = img.getSubimage(33, 0, 31, 64);
    }

    @Override
    public void defineColors() {

    }

    @Override
    public void defineFont() {
    }

    @Override
    public void setup() {

    }

    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        int i = anima_clicked_cooldown;
        if (Simulation.currentlyLiving == 0) {
            if (UI.gstate == UI.GameState.PAUSED)
                g.drawImage(disabled_play_img, x + i, y + i, width - i - i, height - i - i, null);
            else if (UI.gstate == UI.GameState.IN_GAME)
                g.drawImage(disabled_pause_img, x + i, y + i, width - i - i, height - i - i, null);
        } else {

            if (UI.gstate == UI.GameState.PAUSED)
                g.drawImage(play_img, x + i, y + i, width - i - i, height - i - i, null);
            else if (UI.gstate == UI.GameState.IN_GAME)
                g.drawImage(pause_img, x + i, y + i, width - i - i, height - i - i, null);
        }
    }

    @Override
    protected void unselect() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        anima_clicked_cooldown = 5;
        if (Simulation.currentlyLiving != 0) Simulation.toggleGameState();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseWheeled(MouseWheelEvent e) {

    }

    @Override
    public void mouseHover(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
