package de.IchMagOmasKekse.ui;

import de.IchMagOmasKekse.GameLoop;
import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.ui.comp.*;

import java.awt.*;

public class UI {

    public static GameState gstate = GameState.PAUSED;

    public static void createUI() {
        UICCanvas canvas = new UICCanvas(GameLoop.windowWidth, 1, GameLoop.ui_offset, GameLoop.windowHeight);
        canvas.setDynamicSize(true, true);
        canvas.c_infill = new Color(100, 100, 100, 120);
        canvas.c_selected = new Color(0, 0, 0, 0);
        canvas.c_border = new Color(100, 100, 100, 255);
        UICHandler.registerNewUIC(canvas);

        ResetButton btn = new ResetButton(50, 10, 70, 32);
        btn.c_forecolor = Color.DARK_GRAY;
        btn.text = "Neustart";
        canvas.add(btn);

        UICGameState gstatedisplay = new UICGameState(10, 10, 28, 24);
        canvas.add(gstatedisplay);

        GenerationDisplay text = new GenerationDisplay(10, 55, "Arial", 0, 24);
        text.c_forecolor = Color.WHITE;
        text.setText("Generation: "+ Simulation.generations);
        canvas.add(text);

        LivingCellDisplay ltext = new LivingCellDisplay(10, 79, "Arial", 0, 24);
        ltext.c_forecolor = Color.WHITE;
        canvas.add(ltext);

        ScoreDisplay scoreDisplay = new ScoreDisplay(10, 103, "Arial", 0, 24);
        scoreDisplay.c_forecolor = Color.WHITE;
        canvas.add(scoreDisplay);

        GameSpeedSlider slider = new GameSpeedSlider("Geschwindigkeit", 130, 25, 200, 3, 1, 60, UICSlider.SliderType.DEFAULT_50);
        canvas.add(slider);
    }

    public static enum GameState {
        IN_GAME,
        PAUSED,
        MAIN_MENU;
    }

}
