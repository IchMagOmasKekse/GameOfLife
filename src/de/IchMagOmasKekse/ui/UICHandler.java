package de.IchMagOmasKekse.ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;

import de.IchMagOmasKekse.Chat;

public class UICHandler {

    private static UIC selectedUIC = null;
    private static HashMap<String, UIC> uics = new HashMap<>();

    public static boolean isRegistered(String id) {
        return uics.containsKey(id);
    }

    public static void tick() {
        if (getAmountOfRegisteredUICS() != 0) {
            for (UIC uic : uics.values()) uic.tick();
        }
    }

    public static void render(Graphics g) {
        if (getAmountOfRegisteredUICS() != 0) {
            for (UIC uic : uics.values()) uic.render(g);
        }
    }

    public static boolean registerNewUIC(UIC uic) {
        if (!isRegistered(uic.getId())) uics.put(uic.getId(), uic);
        else return false;

        Chat.sendConsoleMessage("Registered new " + uic.getSystemName());
        return true;
    }

    public static boolean unregisterUIC(String id) {
        if (isRegistered(id)) uics.remove(id);
        else return false;
        return true;
    }

    public static void setSelectedUIC(UIC uic) {
        if (selectedUIC != null) {
            selectedUIC.unselectUIC();
        }
        selectedUIC = uic;
        if (uic != null) uic.isSelected = true;
    }

    public static boolean hasSelectedUIC() {
        return selectedUIC == null;
    }

    public static UIC getSelectedUIC() {
        return selectedUIC;
    }

    public static int getAmountOfRegisteredUICS() {
        return uics.size();
    }

    public static UIC getUIC(String id) {
        if (isRegistered(id)) return uics.get(id);
        else return null;
    }

    public static void mouseClicked(MouseEvent e) {
        for (UIC uic : uics.values()) uic.checkMouseClick(e);
    }

    public static void mouseMoved(MouseEvent e) {
        for (UIC uic : uics.values()) uic.checkMouseHover(e);
    }

    public static void mouseReleased(MouseEvent e) {
        for (UIC uic : uics.values()) uic.checkMouseReleased(e);
    }

    public static void mouseDragged(MouseEvent e) {
        for (UIC uic : uics.values()) uic.checkMouseDragged(e);
    }

    public static void mouseWheeled(MouseWheelEvent e) {
        for (UIC uic : uics.values()) uic.checkMouseWheel(e);

    }

    public static void keyPressed(KeyEvent e) {
        for (UIC uic : uics.values()) uic.checkKeyPressed(e);

    }

    public static void keyReleased(KeyEvent e) {
        for (UIC uic : uics.values()) uic.checkKeyReleased(e);

    }

}
