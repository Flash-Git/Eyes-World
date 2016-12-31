package dev.flash.eyesworld.shaders;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.utils.Maths;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by Flash on 30/12/2016.
 */
public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/dev/flash/eyesworld/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/dev/flash/eyesworld/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f matrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
}
