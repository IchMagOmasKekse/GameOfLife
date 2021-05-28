package de.IchMagOmasKekse;

import de.IchMagOmasKekse.simulation.Simulation;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameLoop extends Canvas implements Runnable {



    /* de.IchMagOmasKekse.Window */
    public static int windowWidth = 1200;
    public static int halfWindowWidth = windowWidth / 2;
    public static int windowHeight = 900;
    public static int halfWindowHeight = windowHeight / 2;

    private boolean isRunning = false;
    private boolean isReadyToRender = false;
    private boolean isPaused = false; //Ist das Spiel pausiert?
    public static boolean isLegalTick = false; //Ist der aktuelle tick auf TickSpeed Höhe ?
    private static int fps = 0;
    private int maxFps = 120, tickspeed = 5;
    private Thread thread;
    private Window window;
    public String windowTitle = "1 2"; // Replacements

    /* Instances */
    public static KeyInput keyInput;
    public static MouseInput mouseInput;

    static GameLoop instance;
    public static GameLoop getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new GameLoop();
    }

    public GameLoop() {
        instance = this;
        setBackground(Color.BLACK);
        window = new Window(windowWidth, windowHeight, "GameEngine Preset", this);

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


    @SuppressWarnings("unused")
    @Override
    public void run() {
        /*
         * de.IchMagOmasKekse.GameLoop - Made by Notch
         */
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
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
        if(tick != tickspeed) {
            tick++;
            isLegalTick = false;
        }else {
            isLegalTick = true;
            tick = 0;
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
        Simulation.update();


        window.getFrame().setTitle(Simulation.editTitle(windowTitle).replace("1 ", "FPS: "+fps+" "));
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
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(GameColor.BACKGROUND.getColor());
        g.fillRect(0, 0, windowWidth, windowHeight);
        //START
        //TODO: Translated RENDERING

        Simulation.render(g);

        //ENDE

        //TODO: Static RENDERING

        g.dispose();
        bs.show();
        return true;
    }

}
