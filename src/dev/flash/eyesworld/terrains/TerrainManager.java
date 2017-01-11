package dev.flash.eyesworld.terrains;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flash on 11/01/2017.
 */

public class TerrainManager {
	private List<Terrain> terrains = new ArrayList<>();
	
	public TerrainManager() {
		
	}
	
	public Terrain getTerrain(int x, int z){
		for (Terrain terrain : terrains){
			if(terrain.getX()==x && terrain.getZ()==z){
				return terrain;
			}
		}
		return null;
	}
	
	
	public void addTerain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void removeTerain(Terrain terrain) {
		terrains.remove(terrain);
	}
	
	public void addTerains(List<Terrain> terrains) {
		this.terrains.addAll(terrains);
	}
	
	public void removeTerains(List<Terrain> terrains) {
		for (Terrain terrain : terrains) {
			for (int i = 0; i < this.terrains.size(); i++) {
				if (terrain.equals(this.terrains.get(i))) {
					this.terrains.remove(i);
				}
			}
		}
		
	}
	
}
