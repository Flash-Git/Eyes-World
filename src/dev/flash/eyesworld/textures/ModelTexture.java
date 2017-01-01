package dev.flash.eyesworld.textures;

/**
 * Created by Flash on 30/12/2016.
 */
public class ModelTexture {
	
	public int textureID;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getID() {
		return this.textureID;//this or no this better?
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
}
