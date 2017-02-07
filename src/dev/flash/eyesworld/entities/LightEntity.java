package dev.flash.eyesworld.entities;

import dev.flash.eyesworld.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Flash on 07/01/2017.
 */

public class LightEntity extends Entity {
	
	private Light light;
	private float xOffset;
	private float yOffset;
	private float zOffset;
	
	public LightEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Light light) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.light = light;
		xOffset = light.getPosition().x - position.x;
		yOffset = light.getPosition().y - position.y;
		zOffset = light.getPosition().z - position.z;
		
		//utils.out(xOffset + " " + yOffset + " " + zOffset);
	}
	
	public LightEntity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale, Light light) {
		super(model, textureIndex, position, rotX, rotY, rotZ, scale);
		this.light = light;
		xOffset = light.getPosition().x - position.x;
		yOffset = light.getPosition().y - position.y;
		zOffset = light.getPosition().z - position.z;
	}
	
	@Override
	public void increasePosition(float dx, float dy, float dz) {
		super.increasePosition(dx, dy, dz);
		light.increasePosition(dx, dy, dz);
	}
	
	@Override
	public void setPosition(Vector3f position) {
		super.setPosition(position);
		light.setPosition(new Vector3f(position.x + xOffset, position.y + yOffset, position.z + zOffset));
	}
	
	public Light getLight() {
		return light;
	}
	
}
