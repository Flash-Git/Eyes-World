package dev.flash.eyesworld.guis;

import dev.flash.eyesworld.terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flash on 11/01/2017.
 */

public class GuiManager {
	
	private List<GuiTexture> guis = new ArrayList<>();
	
	public GuiManager() {
	}
	
	public void addGui(GuiTexture gui) {
		guis.add(gui);
	}
	
	public void removeGui(GuiTexture gui) {
		guis.remove(gui);
	}
	
	public void addGuis(List<GuiTexture> guis) {
		this.guis.addAll(guis);
	}
	
	public void removGuis(List<GuiTexture> guis) {
		for (GuiTexture gui : guis) {
			for (int i = 0; i < this.guis.size(); i++) {
				if (gui.equals(this.guis.get(i))) {
					this.guis.remove(i);
				}
			}
		}
	}
	
	//G and S
	
	public List<GuiTexture> getGuis() {
		return guis;
	}
	
	public void setGuis(List<GuiTexture> guis) {
		this.guis = guis;
	}
	
}