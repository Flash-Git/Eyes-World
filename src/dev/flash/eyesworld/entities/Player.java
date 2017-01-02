package dev.flash.eyesworld.entities;

import dev.flash.eyesworld.models.TexturedModel;
import dev.flash.eyesworld.renderEngine.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Flash on 02/01/2017.
 */

public class Player extends Entity {
	
	private static final float RUN_SPEED = 30;//units/second
	private static final float TURN_SPEED = 180;//degrees/second
	private static final float GRAVITY = -75;
	private static final float JUMP_POWER = 45;
	
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private boolean inAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move() {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeMillis() / 1000, 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeMillis() / 1000;
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY()-90)));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY()-90)));
		increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeMillis() / 1000;
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeMillis() / 1000, 0);
		if (super.getPosition().y < TERRAIN_HEIGHT) {
			upwardsSpeed = 0;
			super.getPosition().y = TERRAIN_HEIGHT;
			inAir = false;
		}
	}
	
	private void jump() {
		if (inAir) {
			return;
		}
		upwardsSpeed = JUMP_POWER;
		inAir = true;
	}
	
	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			currentSpeed = RUN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			currentSpeed = -RUN_SPEED / 2;
			
		} else {
			currentSpeed = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			currentTurnSpeed = -TURN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			currentTurnSpeed = TURN_SPEED;
		} else {
			currentTurnSpeed = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
		}
	}
	
	
}
