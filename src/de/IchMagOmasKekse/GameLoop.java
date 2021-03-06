package de.IchMagOmasKekse;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.ui.UICHandler;

public class GameLoop extends Canvas implements Runnable {



    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* de.IchMagOmasKekse.Window */
    //public static int windowWidth = 700; // Default 1200
    public static int windowWidth = 1200; // Default 1200
    public static int halfWindowWidth = windowWidth / 2;
    //public static int windowHeight = 500; // Default 900
    public static int windowHeight = 900; // Default 900
    public static int halfWindowHeight = windowHeight / 2;
    public static final int ui_offset = 500;

    private boolean isRunning = false;
    private boolean isReadyToRender = false;
    @SuppressWarnings("unused")
	private boolean isPaused = false; //Ist das Spiel pausiert?
    public static boolean isLegalTick = false; //Ist der aktuelle tick auf TickSpeed Höhe ?
    private static int fps = 0;
    @SuppressWarnings("unused")
	private int maxFps = 120;
    private Thread thread;
    private Window window;
    public String windowTitle = "FPS WORLDSIZE GEN LIVING OLDEST"; // Replacements

    /* Instances */
    public static KeyInput keyInput;
    public static MouseInput mouseInput;

    static GameLoop instance;
    public static GameLoop getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new GameLoop();
        halfWindowWidth = windowWidth / 2;
        halfWindowHeight = windowHeight / 2;
    }

    public GameLoop() {
        instance = this;
        setBackground(Color.BLACK);
        window = new Window(windowWidth+ui_offset, windowHeight, "GameEngine Preset", this);

        Functions.preInit();
        Functions.init();
        Functions.postInit();

        Chat.sendConsoleMessage("----------------------------------");
        Chat.sendConsoleMessage("Maximale Werte von Datenträgern:");
        Chat.sendConsoleMessage("Byte: " + Byte.MAX_VALUE);
        Chat.sendConsoleMessage("Short: " + Short.MAX_VALUE);
        Chat.sendConsoleMessage("Float: " + Float.MAX_VALUE);
        Chat.sendConsoleMessage("Integer: " + Integer.MAX_VALUE);
        Chat.sendConsoleMessage("Double: " + Double.MAX_VALUE);

        start();
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {e.printStackTrace();}
    }


    @Override
    public void run() {
        /*
         * de.IchMagOmasKekse.GameLoop - Made by Notch
         */
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 240.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                //update++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                frames = 0;
            }
        }
        stop();
    }

    private int tick = 0;
    public void tick() {
        if(tick >= Settings.gameSpeed) {
            isLegalTick = true;
            tick = 0;
        }else {
            isLegalTick = false;
            tick++;
        }
        //TODO: TICK

        //Aktualisiert den INTEGER windowWidht & halfWindowWidht, wenn sich die Fenstergröße verändert hat
        if(window.getFrame().getWidth() != windowWidth)	{
            windowWidth = window.getFrame().getWidth();
            halfWindowWidth = window.getFrame().getWidth() / 2;
        }
        //Aktualisiert den INTEGER windowHeight & halfWindowHeight, wenn sich die Fenstergröße verändert hat
        if(window.getFrame().getHeight() != windowHeight)	{
            windowHeight = window.getFrame().getHeight();
            halfWindowHeight = window.getFrame().getHeight() / 2;
        }
        isReadyToRender = true;

        Chat.tick();
        keyInput.tick();
        mouseInput.tick();
        if(isLegalTick)  Simulation.update();
        if(Settings.updateUICs) UICHandler.tick();


        window.getFrame().setTitle(Simulation.editTitle(windowTitle).replace("FPS", "FPS: "+fps+" "));
    }

    public void asyncTick() {

    }


    public boolean render() {
        if(!isReadyToRender) return false;
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return false;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(GameColor.BACKGROUND.getColor());
        g.fillRect(0, 0, windowWidth, windowHeight);
        //START
        //TODO: Translated RENDERING

        Simulation.render(g);
        if(Settings.showUICs) UICHandler.render(g);

        //ENDE

        //TODO: Static RENDERING

        g.dispose();
        bs.show();
        return true;
    }

}
