package dev.flash.eyesworld.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Flash on 31/12/2016.
 */

public class Camera {
	
	private Vector3f position = new Vector3f(0, 1, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {}
	
	public void move(){
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)){
			position.z -=0.2f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z +=0.2f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x +=0.2f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
			position.x -=0.2f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y +=0.2f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			position.y -=0.2f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_N)){
			roll +=1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw +=1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw -=1f;
		}
		
		
	}
	
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
