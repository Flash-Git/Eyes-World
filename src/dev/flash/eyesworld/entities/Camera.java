package dev.flash.eyesworld.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Flash on 31/12/2016.
 */

public class Camera {
	
	private Vector3f position = new Vector3f(100, 35, 50);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {}
	
	
	//G and S
	
	public Vector3f getPosition() {
		return position;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getRoll() {
		return roll;
	}
}
