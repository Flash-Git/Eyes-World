package dev.flash.eyesworld.water;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.shaders.ShaderProgram;
import dev.flash.eyesworld.utils.Maths;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Created by Flash on 04/01/2017.
 */

public class WaterShader extends ShaderProgram {
	
	private final static String VERTEX_FILE = "src/dev/flash/eyesworld/water/waterVertex.glsl";
	private final static String FRAGMENT_FILE = "src/dev/flash/eyesworld/water/waterFragment.glsl";
	
	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	
	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadModelMatrix(Matrix4f modelMatrix) {
		loadMatrix(location_modelMatrix, modelMatrix);
	}
	
}
