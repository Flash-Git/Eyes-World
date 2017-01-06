package dev.flash.eyesworld.textures;

/**
 * Created by Flash on 30/12/2016.
 */

public class ModelTexture {
	
	private int textureID;
	private int normalMap;
	
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean transparency = false;
	private boolean fakeLighting = false;
	
	private int numberOfRows = 1;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getID() {
		return this.textureID;//this or no this better?
	}
	
	public int getNormalMap() {
		return normalMap;
	}
	
	public void setNormalMap(int normalMap) {
		this.normalMap = normalMap;
	}
	
	public float getShineDamper() {
		return shineDamper;
	}
	
	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
	
	public float getReflectivity() {
		return reflectivity;
	}
	
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	public boolean hasTransparency() {
		return transparency;
	}
	
	public void setTransparency(boolean transparency) {
		this.transparency = transparency;
	}
	
	public boolean hasFakeLighting() {
		return fakeLighting;
	}
	
	public void setFakeLighting(boolean fakeLighting) {
		this.fakeLighting = fakeLighting;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}
	
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
}
