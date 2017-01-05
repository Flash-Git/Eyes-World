package dev.flash.eyesworld.water;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.entities.Light;
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
	private int location_reflectionTexture;
	private int location_refractionTexture;
	private int location_dudvMap;
	private int location_moveFactor;
	private int location_cameraPosition;
	private int location_normalMap;
	private int location_lightColour;
	private int location_lightPosition;
	
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
		location_reflectionTexture = getUniformLocation("reflectionTexture");
		location_refractionTexture = getUniformLocation("refractionTexture");
		location_dudvMap = getUniformLocation("dudvMap");
		location_moveFactor = getUniformLocation("moveFactor");
		location_cameraPosition = getUniformLocation("cameraPosition");
		location_normalMap = getUniformLocation("normalMap");
		location_lightColour = getUniformLocation("lightColour");
		location_lightPosition = getUniformLocation("lightPosition");
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_cameraPosition, camera.getPosition());
	}
	
	public void loadModelMatrix(Matrix4f modelMatrix) {
		loadMatrix(location_modelMatrix, modelMatrix);
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_reflectionTexture, 0);
		super.loadInt(location_refractionTexture, 1);
		super.loadInt(location_dudvMap, 2);
		super.loadInt(location_normalMap, 3);
	}
	
	public void loadMoveFactor(float moveFactor) {
		super.loadFloat(location_moveFactor, moveFactor);
	}
	
	public void loadLight(Light sun) {
		super.loadVector(location_lightColour, sun.getColour());
		super.loadVector(location_lightPosition, sun.getPosition());
	}
}
