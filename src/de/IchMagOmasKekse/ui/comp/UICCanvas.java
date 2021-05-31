package de.IchMagOmasKekse.ui.comp;

import de.IchMagOmasKekse.Chat;
import de.IchMagOmasKekse.GameColor;
import de.IchMagOmasKekse.ui.UIC;
import de.IchMagOmasKekse.ui.UICHandler;
import de.IchMagOmasKekse.ui.UICNames;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class UICCanvas extends UIC {

    private HashMap<String, UIC> childs = new HashMap<>();
    private ArrayList<UIC> selected = new ArrayList<>();

    public UICCanvas(int x, int y, int width, int height) {
        super(UICNames.UIC_CANVAS.getSystemName(), x, y, width, height);
        defineColors();
        defineFont();
    }

    @Override
    public void defineColors() {
        // Default colors
        c_infill = GameColor.UIC_INFILL.getColor();
        c_border = GameColor.UIC_BORDER.getColor();
        c_clicked = GameColor.UIC_CLICKED.getColor();
        c_selected = GameColor.UIC_SELECTED.getColor();
        c_highlighted = new Color(0, 0, 0, 0);
        c_forecolor = GameColor.UIC_FORECOLOR.getColor();
        c_disabled = GameColor.UIC_DISABLED.getColor();
    }

    @Override
    public void defineFont() {
        font = new Font("Arial", 0, 14);
    }

    @Override
    public void setup() {

    }

    @Override
    public void update() {
        if (!childs.isEmpty()) {
            for (UIC uic : childs.values()) uic.tick();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        drawRect(g); // Drawing background

        if (!childs.isEmpty()) {
            for (UIC uic : childs.values()) uic.render(g);
        }
    }

    @Override
    public void unselect() {
        if (!selected.isEmpty()) {
            for (UIC uic : selected) uic.unselectUIC();
        }
        selected.clear();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (UIC uic : childs.values()) {
            if (uic.isInComponentBounds(e.getX(), e.getY())) {
                selected.add(uic);
                uic.select();
                UICHandler.setSelectedUIC(uic);
                uic.mouseClicked(e);
                break;
            }
        }
        UICHandler.setSelectedUIC(this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /**
         *         if (!selected.isEmpty()) {
         *             for (UIC uic : selected) {
         *                 uic.checkMouseReleased(e);
         *             }
         *         }
         */

        if (!childs.isEmpty()) {
            for (UIC uic : childs.values()) {
                uic.checkMouseReleased(e);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseWheeled(MouseWheelEvent e) {
        if (!selected.isEmpty()) {
            for (UIC uic : selected) {
                uic.checkMouseWheel(e);
            }
        }
    }

    @Override
    public void mouseHover(MouseEvent e) {
        if (!childs.isEmpty()) {
            for (UIC uic : childs.values()) {
                uic.checkMouseHover(e);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_H:
                hide = !hide;
                break;
        }
        if (!selected.isEmpty()) {
            for (UIC uic : selected) {
                uic.checkKeyPressed(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!selected.isEmpty()) {
            for (UIC uic : selected) {
                uic.checkKeyReleased(e);
            }
        }
    }

    public boolean add(UIC uic) {
        if (childs.containsKey(uic.getId())) return false;
        else childs.put(uic.getId(), uic);
        uic.setParent(this);
        Chat.sendConsoleMessage("Addet " + uic.getSystemName() + " to Canvas");
        return true;
    }

    public boolean remove(String id) {
        if (childs.containsKey(id)) childs.remove(id);
        else return false;
        return true;
    }
}
