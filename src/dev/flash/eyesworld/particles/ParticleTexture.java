package dev.flash.eyesworld.particles;

/**
 * Created by Flash on 25/01/2017.
 */

public class ParticleTexture {
	
	private int textureID;
	private int numOfRows;
	private boolean additive = false;
	
	public ParticleTexture(int textureID, int numOfRows) {
		this.textureID = textureID;
		this.numOfRows = numOfRows;
	}
	
	public ParticleTexture(int textureID, int numOfRows, boolean additive) {
		this.textureID = textureID;
		this.numOfRows = numOfRows;
		this.additive = additive;
	}
	
	public int getTextureID() {
		return textureID;
	}
	
	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}
	
	public int getNumOfRows() {
		return numOfRows;
	}
	
	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}
	
	public boolean isAdditive() {
		return additive;
	}
	
	public void setAdditive(boolean additive) {
		this.additive = additive;
	}
}
