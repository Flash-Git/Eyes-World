package dev.flash.eyesworld.skybox;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.shaders.ShaderProgram;
import dev.flash.eyesworld.utils.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Flash on 03/01/2017.
 */

public class SkyboxShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/dev/flash/eyesworld/skybox/skyboxVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/dev/flash/eyesworld/skybox/skyboxFragmentShader.glsl";
	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_fogColour;
	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	
	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_fogColour = super.getUniformLocation("fogColour");
	}
	
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
	public void loadFogColour(float r, float g, float b) {
		super.loadVector(location_fogColour, new Vector3f(r, g, b));
	}
	
}
