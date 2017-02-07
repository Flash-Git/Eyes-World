package dev.flash.eyesworld.terrains;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flash on 11/01/2017.
 */

public class TerrainManager {
	private List<Terrain> terrains = new ArrayList<>();
	private Terrain dummyTerrain;
	
	public TerrainManager() {
	}
	
	public Terrain getTerrain(float x, float z) {
		for (Terrain terrain : terrains) {
			if (terrain.getX() == Math.floor(x / Terrain.SIZE) * Terrain.SIZE && terrain.getZ() == Math.floor(z / Terrain.SIZE) * Terrain.SIZE) {
				return terrain;
			}
		}
		return dummyTerrain;
	}
	
	public void addTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void removeTerrain(Terrain terrain) {
		terrains.remove(terrain);
	}
	
	public void addTerrains(List<Terrain> terrains) {
		this.terrains.addAll(terrains);
	}
	
	public void removeTerrains(List<Terrain> terrains) {
		for (Terrain terrain : terrains) {
			for (int i = 0; i < this.terrains.size(); i++) {
				if (terrain.equals(this.terrains.get(i))) {
					this.terrains.remove(i);
				}
			}
		}
		
	}
	
	//G and S
	
	public List<Terrain> getTerrains() {
		return terrains;
	}
	
	public void setTerrains(List<Terrain> terrains) {
		this.terrains = terrains;
	}
	
	public Terrain getDummyTerrain() {
		return dummyTerrain;
	}
	
	public void setDummyTerrain(Terrain dummyTerrain) {
		this.dummyTerrain = dummyTerrain;
	}
	
}
