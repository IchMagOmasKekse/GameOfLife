package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.Chat;
import de.IchMagOmasKekse.GameLoop;
import de.IchMagOmasKekse.Settings;
import de.IchMagOmasKekse.ui.UIC;
import de.IchMagOmasKekse.ui.UICNames;
import javafx.scene.control.Slider;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class UICSlider extends UIC {

    private final int startAndEndBlock_width = 4;
    private final int startAndEndBlock_height = 12;
    private final int sliderWidth = 4;
    private final int sliderHeight = 15;
    private int sliderValue = 0;
    private double sliderValueInPercent = 0;
    private final int minValue, maxValue;
    private int sliderPos = 0;
    private boolean listenMouse = false;
    private SliderType type;

    public UICSlider(String text, int x, int y, int width, int height, int minValue, int maxValue, SliderType type) {
        super(UICNames.UIC_SLIDER.getSystemName(), x, y, width, height);
        this.text = text;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.type = type;
        defineColorsToDefault();
        font = new Font("Arial", Font.PLAIN, 16);

        switch (type) {
            case DEFAULT_0: sliderPos = 0; break;
            case DEFAULT_50: sliderPos = width/2; break;
            case DEFAULT_100: sliderPos = width; break;
        }

        calcValue();
    }

    @Override
    public void defineColors() {

    }

    @Override
    public void defineFont() {

    }

    @Override
    public void setup() {
        if (parent != null) {
            hitboxX = this.x;
            hitboxY = (this.y - (startAndEndBlock_height / 2)) - 5;
            hitboxWidth = width;
            hitboxHeight = height + startAndEndBlock_height + 10;
        }
    }

    @Override
    public void update() {
        if(listenMouse) {
            if(GameLoop.mouseInput.getCurrentMouseX() < getX()) sliderPos = 0;
            else if(GameLoop.mouseInput.getCurrentMouseX() > getX()+width) sliderPos = width;
            else sliderPos = GameLoop.mouseInput.getCurrentMouseX() - getX();

            calcValue();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(text + " = " + sliderValue, x, y - (font.getSize() / 2));
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.fillRect(x - (startAndEndBlock_width / 2), y - (startAndEndBlock_height / 2), startAndEndBlock_width, startAndEndBlock_height + height); // Left End
        g.fillRect(x + width - (startAndEndBlock_width / 2), y - (startAndEndBlock_height / 2), startAndEndBlock_width, startAndEndBlock_height + height); // Right End

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x + sliderPos, y - (sliderHeight / 2), sliderWidth, sliderHeight + height);
    }

    @Override
    protected void unselect() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isDoubleClicked()) {
            switch (type) {
                case DEFAULT_0: sliderPos = 0; break;
                case DEFAULT_50: sliderPos = width/2; break;
                case DEFAULT_100: sliderPos = width; break;
            }
            calcValue();
        }else listenMouse = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        listenMouse = false;
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

    @Override
    public void setParent(UIC uic) {
        super.setParent(uic);
        setup();
    }

    public void calcValue() {
        int einPro = width / 100;
        sliderValueInPercent  = (int) (sliderPos / einPro);
        sliderValue = (int)((maxValue - minValue) * (sliderValueInPercent / 100)) + minValue;

        GameLoop.setSpeed(sliderValue);
    }

    public static enum SliderType {
        DEFAULT_0,
        DEFAULT_50,
        DEFAULT_100;
    }
}
