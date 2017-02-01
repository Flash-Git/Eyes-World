package dev.flash.eyesworld.particles;

import org.lwjgl.util.vector.Matrix4f;

import dev.flash.eyesworld.shaders.ShaderProgram;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by Flash on 20/01/2017.
 */

public class ParticleShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/dev/flash/eyesworld/particles/particleVShader.txt";
	private static final String FRAGMENT_FILE = "src/dev/flash/eyesworld/particles/particleFShader.txt";
	
	private int location_projectionMatrix;
	private int location_numberOfRows;
	
	
	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "texOffsets");
		super.bindAttribute(6, "blendFactor");
	}
	
	protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
	
	protected void loadNumberOfRows(float numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}
	
}
