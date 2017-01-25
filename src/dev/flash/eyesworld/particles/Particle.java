package dev.flash.eyesworld.particles;

import dev.flash.eyesworld.entities.Player;
import dev.flash.eyesworld.renderEngine.DisplayManager;
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
	
	private float elapsedTime = 0;
	
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
	
	public boolean update() {
		velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeMillis() / 1000;
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeMillis() / 1000);
		Vector3f.add(change, position, position);//TODO look at this
		elapsedTime += DisplayManager.getFrameTimeMillis() / 1000;
		return elapsedTime < lifeLength;
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
}
