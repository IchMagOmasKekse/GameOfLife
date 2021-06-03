package de.IchMagOmasKekse.ui.comp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

import de.IchMagOmasKekse.*;
import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.simulation.generation.GenerationManager;
import de.IchMagOmasKekse.ui.UIC;
import de.IchMagOmasKekse.ui.UICNames;

public class UICItemList extends UIC {

    private ConcurrentLinkedDeque<Item> items;
    private int maxCols; // max amount of columns
    private int itemDisplaySize; // size of each item
    private int spaceBetween; // Space between each item
    private int addItems; // Amount of items which has to be added

    public UICItemList(int x, int y, int width, int height, String text) {
        super(UICNames.UIC_ITEM_LIST.getSystemName(), x, y, width, height, text);
        defineColorsToDefault();
        defineFontToDefault();
        this.c_forecolor = Color.WHITE;
        this.c_infill = Color.BLACK;
    }

    @Override
    public void defineColors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void defineFont() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setup() {
        maxCols = 4;
        spaceBetween = 10;
        addItems = 10;
        this.itemDisplaySize = (width - ((maxCols + 1) * spaceBetween)) / maxCols;
        items = new ConcurrentLinkedDeque<Item>();
        int row = 0;
        while (items.size() < addItems) {
            for (int col = 0; col != maxCols; col++) {
                items.add(new Item(this, "preset_" + items.size(), row, col, itemDisplaySize));
                if (items.size() >= addItems) break;
            }
            if (items.size() >= addItems) break;
            row++;
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void draw(Graphics2D g) {
        drawRect(g);

        g.setColor(c_forecolor);
        g.drawString(text, x + 5, y - 5);

        if (!items.isEmpty()) {
            for (Item item : items) item.draw(g);
        }
    }

    @Override
    protected void unselect() {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!items.isEmpty()) {
            for (Item item : items) item.checkClick(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseWheeled(MouseWheelEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseHover(MouseEvent e) {
        if (!items.isEmpty()) {
            for (Item item : items) item.checkMouse(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    private class Item {

        private String name = "";
        private int x, y, size, xspace = 10, yspace = 10;
        private UICItemList il;
        private boolean leftRow = false, itemHighlighted = false;
        private HashMap<Coordinate, Boolean> livingCells = new HashMap<Coordinate, Boolean>();

        public Item(UICItemList iList, String name, int row, int col, int size) {
            this.il = iList;
            this.name = name;
            this.y = row * size + (row * yspace);
            this.x = col * size + (col * xspace);
            this.size = size;
            livingCells = PresetReader.read(name);

            //if(col % 2 == 1) xspace *= 2;
        }

        public void draw(Graphics g) {
            if (leftRow) g.setColor(Color.RED);
            if (itemHighlighted) g.setColor(GameColor.UIC_ITEM_LIST_ITEM_HIGHLIGHTED.getColor());
            else g.setColor(GameColor.UIC_ITEM_LIST_ITEM_BACKGROUND.getColor());
            g.fillRect(il.getX() + x + spaceBetween, il.getY() + y + spaceBetween, size, size);
            g.setColor(Color.white);
            g.drawString(name, il.getX() + x + 20, il.getY() + y + 30);
        }

        public boolean checkMouse(MouseEvent e) {
            if (e.getX() > il.getX() + x + spaceBetween && e.getX() < il.getX() + x + spaceBetween + itemDisplaySize &&
                    e.getY() > il.getY() + y + spaceBetween && e.getY() < il.getY() + y + spaceBetween + itemDisplaySize) {
                itemHighlighted = true;
                return true;
            } else {
                itemHighlighted = false;
                return false;
            }
        }

        public void checkClick(MouseEvent e) {
            if (checkMouse(e)) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    GenerationManager.currentGeneration.killAll();

                    if(livingCells != null && !livingCells.isEmpty()) {
                        for (Coordinate c : livingCells.keySet()) {
                            GenerationManager.currentGeneration.birthCellAt(c.getxOff(), c.getyOff());
                        }
                    }
                }else if(e.getButton() == MouseEvent.BUTTON3 && KeyInput.isAlting) {
                    livingCells = new HashMap<>();
                    for (int x = GameLoop.halfWindowWidth * (-1); x < GameLoop.halfWindowWidth; x++) {
                        for (int y = GameLoop.halfWindowHeight * (-1); y < GameLoop.halfWindowHeight; y++) {

                            if (GenerationManager.getCellFromCurrentGen(GameLoop.halfWindowWidth + x, GameLoop.halfWindowHeight + y) != null &&
                                    GenerationManager.getCellFromCurrentGen(GameLoop.halfWindowWidth + x, GameLoop.halfWindowHeight + y).isAlive())
                                livingCells.put(new Coordinate(GameLoop.halfWindowWidth + x, GameLoop.halfWindowHeight + y), true);
                        }
                    }
                    PresetWriter.write(name, livingCells);
                }
            }
        }

    }

    public static class Coordinate {
        private int xOff, yOff;

        public Coordinate(int xOff, int yOff) {
            this.xOff = xOff;
            this.yOff = yOff;
        }

        public int getxOff() {
            return xOff;
        }

        public int getyOff() {
            return yOff;
        }
    }

    protected static class PresetReader {

        protected static HashMap<Coordinate, Boolean> read(String name) {
            try {
                Scanner scanner = new Scanner(new File("assets/" + name + ".txt"));
                ArrayList<String> lines = new ArrayList<String>();

                while (scanner.hasNextLine()) {
                    lines.add(scanner.nextLine());
                }

                HashMap<Coordinate, Boolean> livingCells = new HashMap<>();

                String[] sp;
                for(String s : lines) {
                    if(s.startsWith("    - '") && s.endsWith("'")) {
                        sp = s.replace("    - '", "")
                                .replace("'", "")
                                .split(":");

                        livingCells.put(new Coordinate(Integer.parseInt(sp[0]),
                                Integer.parseInt(sp[1])),
                                (sp[2].equals("alive")));
                    }
                }
                return livingCells;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }

            return null;
        }

    }

    protected static class PresetWriter {

        protected static void write(String name, HashMap<Coordinate, Boolean> cells) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("assets/" + name + ".txt")));
                writer.write("Preset:");
                writer.newLine();
                writer.write("  Name: '" + name + "'");
                writer.newLine();
                writer.write("  Cells:");
                writer.newLine();
                for (Coordinate c : cells.keySet()) {
                    if (cells.get(c)) {
                        writer.write("    - '" + c.getxOff() + ":" + c.getyOff() + ":" + (cells.get(c) ? "alive" : "dead") + "'");
                        writer.newLine();
                    }
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
