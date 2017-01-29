package dev.flash.eyesworld.water;


/**
 * Created by Flash on 04/01/2017.
 */

public class WaterTile {
	
	public static final float TILE_SIZE = 512;
	
	private float height;
	private float x, z;
	
	public WaterTile(float centerX, float centerZ, float height) {
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getX() {
		return x;
	}
	
	public float getZ() {
		return z;
	}
	
	
}
