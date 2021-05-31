package de.IchMagOmasKekse;

import de.IchMagOmasKekse.simulation.Simulation;
import de.IchMagOmasKekse.simulation.generation.GenerationManager;
import de.IchMagOmasKekse.ui.UI;
import de.IchMagOmasKekse.ui.UICHandler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentLinkedDeque;

@SuppressWarnings("unused")
public class KeyInput extends KeyAdapter {

	private ConcurrentLinkedDeque<Integer> pressedKeys = new ConcurrentLinkedDeque<Integer>();
	public static boolean isShifting = false;
	public static boolean isAlting = false;
	public static boolean isControlling = false;

	public KeyInput() {	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(!pressedKeys.contains(key)) pressedKeys.add(key);
		UICHandler.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		releaseKey(key);
		UICHandler.keyReleased(e);
	}

	public boolean pressKey(int key) {
		switch(key) {
			case KeyEvent.VK_ENTER:
				Simulation.start = !Simulation.start;
				if(Simulation.start) UI.gstate = UI.GameState.IN_GAME;
				else UI.gstate = UI.GameState.PAUSED;
				releaseKey(key);
				break;
			case KeyEvent.VK_G:
				Simulation.nextGeneration();
				releaseKey(key);
				break;
			case KeyEvent.VK_R:
				Simulation.reset();
				releaseKey(key);
				break;
			case KeyEvent.VK_SHIFT: isShifting = true; break; //Gibt für alle Klassen kund, dass Shift gedrückt wird
			case KeyEvent.VK_CONTROL: isControlling = true; break; //Gibt für alle Klassen kund, dass Strg gedrückt wird
			case KeyEvent.VK_ALT: isAlting = true; break; //Gibt für alle Klassen kund, dass Strg gedrückt wird
		}
		return true;
	}

	public void releaseKey(int key) {
		if(pressedKeys.contains(key)) pressedKeys.remove(key);
		switch(key) {
			case KeyEvent.VK_ESCAPE: System.exit(0); break; //Spiel beenden
			case KeyEvent.VK_SHIFT: isShifting = false; break;
			case KeyEvent.VK_CONTROL: isControlling = false; break;
			case KeyEvent.VK_ALT: isAlting = false; break;
		}
	}

	/**
	 * Geht alle gedrückten Keys durch, um ein automatischen drücken zu ermöglichen.
	 */
	public void tick() {
		if(pressedKeys.isEmpty()) return;

		for(int key : pressedKeys) {
			pressKey(key);
		}
	}

}
