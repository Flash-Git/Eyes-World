package dev.flash.eyesworld.particles;

/**
 * Created by Flash on 25/01/2017.
 */

public class ParticleTexture {
	
	private int textureID;
	private int numOfRows;
	
	public ParticleTexture(int textureID, int numOfRows) {
		this.textureID = textureID;
		this.numOfRows = numOfRows;
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
}
