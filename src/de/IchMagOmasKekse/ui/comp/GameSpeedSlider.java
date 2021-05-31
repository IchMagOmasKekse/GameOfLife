package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.GamePlay;
import de.IchMagOmasKekse.Settings;

public class GameSpeedSlider extends UICSlider {

    public GameSpeedSlider(String text, int x, int y, int width, int height, int minValue, int maxValue, SliderType type) {
        super(text, x, y, width, height, minValue, maxValue, type);
    }

    @Override
    public void tick() {
        super.tick();
        Settings.gameSpeed = this.sliderValue;
        text = "Geschwindigkeit: ";
        if(sliderValue >= GamePlay.SpeedType.SLUG_SPEED.getMin() && sliderValue <= GamePlay.SpeedType.SLUG_SPEED.getMax()) text += GamePlay.SpeedType.SLUG_SPEED.getName();
        else if(sliderValue >= GamePlay.SpeedType.SLOWER.getMin() && sliderValue <= GamePlay.SpeedType.SLOWER.getMax()) text += GamePlay.SpeedType.SLOWER.getName();
        else if(sliderValue >= GamePlay.SpeedType.SLOW.getMin() && sliderValue <= GamePlay.SpeedType.SLOW.getMax()) text += GamePlay.SpeedType.SLOW.getName();
        else if(sliderValue >= GamePlay.SpeedType.NORMAL.getMin() && sliderValue <= GamePlay.SpeedType.NORMAL.getMax()) text += GamePlay.SpeedType.NORMAL.getName();
        else if(sliderValue >= GamePlay.SpeedType.FAST.getMin() && sliderValue <= GamePlay.SpeedType.FAST.getMax()) text += GamePlay.SpeedType.FAST.getName();
        else if(sliderValue >= GamePlay.SpeedType.ULTRA.getMin() && sliderValue <= GamePlay.SpeedType.ULTRA.getMax()) text += GamePlay.SpeedType.ULTRA.getName();
        else if(sliderValue >= GamePlay.SpeedType.INSANE.getMin() && sliderValue <= GamePlay.SpeedType.INSANE.getMax()) text += GamePlay.SpeedType.INSANE.getName();
    }
}
