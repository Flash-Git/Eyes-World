package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.entities.Entity;
import dev.flash.eyesworld.entities.Light;
import dev.flash.eyesworld.models.TexturedModel;
import dev.flash.eyesworld.renderEngine.*;
import dev.flash.eyesworld.models.RawModel;
import dev.flash.eyesworld.shaders.StaticShader;
import dev.flash.eyesworld.terrains.Terrain;
import dev.flash.eyesworld.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Flash on 29/12/2016.
 */

public class MainGameLoop {
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);
		
		Terrain terrain = new Terrain(-1, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain3 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain4 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain5 = new Terrain(-1, -2, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain6 = new Terrain(0, -2, loader, new ModelTexture(loader.loadTexture("grass")));
		
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		
		grass.getTexture().setTransparency(true);
		grass.getTexture().setFakeLighting(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800*2 - 400*2, 0, random.nextFloat() * -600*4), 0, 0, 0, 3));
		}
		
		Light light = new Light(new Vector3f(0, 500, -20), new Vector3f(1, 1, 1));
		
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while (!Display.isCloseRequested()) {
			entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, 0.15f, 0);
			camera.move();
			
			renderer.processEntity(entity);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processTerrain(terrain3);
			renderer.processTerrain(terrain4);
			renderer.processTerrain(terrain5);
			renderer.processTerrain(terrain6);
			
			for (Entity e:entities)
				renderer.processEntity(e);
			
			renderer.render(light, camera);
			
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
