package de.IchMagOmasKekse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TextureAtlas {
	
	private BufferedImage image;
	private HashMap<String, BufferedImage> textures;
	public boolean isReady = false; //Wichtig für die Game Klasse. Diese rendert nur dann, wenn der TextureAtlas ready ist
	
	public TextureAtlas() {
		this.textures = new HashMap<String, BufferedImage>();
		reloadTextures();
	}
	
	public BufferedImage loadImage(String path) {
		try {
			System.out.println("Loading Texture: [./assets//]" + path);
			image = ImageIO.read(new File("./assets/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public void reloadTextures() {
		/**/
		isReady = false;
		System.out.println("------------- Lade Texturen ------------");
		/* Lädt alle Texturen neu und fügt sie zum Atlas(textures(hashmap)) hinzu */
		textures.clear();
		//for(int i = 0; i < Material.values().length; i++) {
			//Material material = Material.values()[i];
			//textures.put(material.getName(), loadImage(material.getFilePath()));
		//}
		textures.put("play_pause", loadImage("/play_pause.png"));
		textures.put("disabled_play_pause", loadImage("/disabled_play_pause.png"));
		textures.put("button", loadImage("/button.png"));
		textures.put("cell_bloom_overlap", loadImage("/cell_bloom_overlap.png"));
		textures.put("cell_baby", loadImage("/cell_baby.png"));
		textures.put("cell_kind", loadImage("/cell_kind.png"));
		textures.put("cell_jugendlich", loadImage("/cell_jugendlich.png"));
		textures.put("cell_adult", loadImage("/cell_adult.png"));
		textures.put("cell_reif", loadImage("/cell_reif.png"));
		textures.put("cell_rentner", loadImage("/cell_rentner.png"));

		if(textures.size() < 10) System.out.println("---------- " + textures.size() + " Texturen geladen ----------");
		else if(textures.size() > 10 && textures.size() < 100) System.out.println("--------- " + textures.size() + " Texturen geladen ----------");
		else if(textures.size() > 100 && textures.size() < 1000) System.out.println("-------- " + textures.size() + " Texturen geladen ----------");
		isReady = true;
	}
	
	public HashMap<String, BufferedImage> getTextures() {
		return textures;
	}
	public BufferedImage getTexture(String name) {
		return textures.get(name);
	}

}
