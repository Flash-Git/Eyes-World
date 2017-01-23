package dev.flash.eyesworld.particles;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.renderEngine.Loader;
import dev.flash.eyesworld.utils.Utils;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Flash on 23/01/2017.
 */

public class ParticleMaster {
	private static List<Particle> particles = new ArrayList<>();
	private static ParticleRenderer renderer;
	
	public static void init(Loader loader, Matrix4f projectionMatrix) {
		renderer = new ParticleRenderer(loader, projectionMatrix);
	}
	
	public static void update() {
		//Utils.out(particles.size());
		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext()) {
			Particle p = iterator.next();
			boolean stillAlive = p.update();
			if (!stillAlive) {
				iterator.remove();
			}
			
		}
	}
	
	public static void renderParticles(Camera camera) {
		renderer.render(particles, camera);
	}
	
	public static void cleanUp() {
		renderer.cleanUp();
	}
	
	public static void addParticle(Particle particle) {
		particles.add(particle);
	}
	
	
}
