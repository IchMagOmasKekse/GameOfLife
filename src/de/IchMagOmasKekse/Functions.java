package de.IchMagOmasKekse;

import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.simulation.generation.GenerationManager;
import de.IchMagOmasKekse.ui.UI;

public class Functions {

    public static boolean isReady = false;
    public static int worldWidth = 100;
    public static int worldHeight = 100;

    /**
     * Erledigt das Erstellen von relevanten Instanzen
     */
    public static void preInit() {
        new Simulation();
        new Chat();
        GameLoop.keyInput = new KeyInput();
        GameLoop.mouseInput = new MouseInput();
        Simulation.textureAtlas = new TextureAtlas();
    }

    /**
     * Lädt Dateien
     */
    public static void init() {
    }

    /**
     * Registriert Listeners/Adapters/etc.
     */
    public static void postInit() {
        GameLoop.getInstance().addKeyListener(GameLoop.keyInput);
        GameLoop.getInstance().addMouseListener(GameLoop.mouseInput);
        GameLoop.getInstance().addMouseMotionListener(GameLoop.mouseInput);
        GameLoop.getInstance().addMouseWheelListener(GameLoop.mouseInput);

        new UI();
        UI.createUI();

        isReady = true;
    }

}
