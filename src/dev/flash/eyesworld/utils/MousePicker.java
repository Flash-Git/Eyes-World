package dev.flash.eyesworld.utils;

import dev.flash.eyesworld.entities.Camera;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * Created by Flash on 04/01/2017.
 */

public class MousePicker {
	
	protected Vector3f currentRay;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	protected Camera camera;
	
	public MousePicker(Camera camera, Matrix4f projectionMatrix) {
		this.camera = camera;
		this.projectionMatrix = projectionMatrix;
		this.viewMatrix = Maths.createViewMatrix(camera);
	}
	
	public void update() {
		viewMatrix = Maths.createViewMatrix(camera);
		currentRay = calculateMouseRay();
	}
	
	private Vector3f calculateMouseRay() {
		//get mouse in viewport space
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		
		//convert to normalised device space (opengl coord system)
		Vector2f normalisedCoords = getNormalisedDeviceCoords(mouseX, mouseY);
		
		//convert to homogeneous clip space
		Vector4f clipCoords = new Vector4f(normalisedCoords.x, normalisedCoords.y, -1f, -1f);
		
		//convert to eye space
		Vector4f eyeCoords = getEyeCoordsFromClip(clipCoords);
		
		//convert to world space
		Vector3f worldRay = getWorldCoordsFromEye(eyeCoords);
		
		return worldRay;
	}
	
	private Vector2f getNormalisedDeviceCoords(float mouseX, float mouseY) {
		float x = (2.0f * mouseX) / Display.getWidth() - 1f;
		float y = (2.0f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}
	
	private Vector4f getEyeCoordsFromClip(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}
	
	private Vector3f getWorldCoordsFromEye(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f worldCoords = Matrix4f.transform(invertedView, eyeCoords, null);//WorldRay
		Vector3f mouseRay = new Vector3f(worldCoords.x, worldCoords.y, worldCoords.z);
		mouseRay.normalise();
		return mouseRay;
	}
	
	
	public Vector3f getCurrentRay() {
		return currentRay;
	}
}
