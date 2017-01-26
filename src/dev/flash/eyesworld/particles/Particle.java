package dev.flash.eyesworld.particles;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.entities.Player;
import dev.flash.eyesworld.renderEngine.DisplayManager;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Flash on 20/01/2017.
 */

public class Particle {
	
	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;//1 is normal gravity
	private float lifeLength;
	private float rotation;
	private float scale;
	
	private ParticleTexture texture;
	
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;
	
	private float elapsedTime = 0;
	private float distance;
	
	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale) {
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}
	
	public boolean update(Camera camera) {
		velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeMillis() / 1000;
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeMillis() / 1000);
		Vector3f.add(change, position, position);//TODO look at this
		distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		updateTextureCoordInfo();
		elapsedTime += DisplayManager.getFrameTimeMillis() / 1000;
		return elapsedTime < lifeLength;
	}
	
	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumOfRows() * texture.getNumOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
	}
	
	private void setTextureOffset(Vector2f offset, int index){
		int column = index % texture.getNumOfRows();
		int row = index / texture.getNumOfRows();
		offset.x = (float) column/texture.getNumOfRows();
		offset.y = (float) row/texture.getNumOfRows();
	}
	
	//G and S
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getVelocity() {
		return velocity;
	}
	
	public float getGravityEffect() {
		return gravityEffect;
	}
	
	public float getLifeLength() {
		return lifeLength;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public float getScale() {
		return scale;
	}
	
	public float getElapsedTime() {
		return elapsedTime;
	}
	
	public ParticleTexture getTexture() {
		return texture;
	}
	
	public Vector2f getTexOffset1() {
		return texOffset1;
	}
	
	public Vector2f getTexOffset2() {
		return texOffset2;
	}
	
	public float getBlend() {
		return blend;
	}
	
	public float getDistance() {
		return distance;
	}
}
