package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.entities.Entity;
import dev.flash.eyesworld.entities.Light;
import dev.flash.eyesworld.entities.Player;
import dev.flash.eyesworld.guis.GuiRenderer;
import dev.flash.eyesworld.guis.GuiTexture;
import dev.flash.eyesworld.models.RawModel;
import dev.flash.eyesworld.models.TexturedModel;
import dev.flash.eyesworld.objConverter.ModelData;
import dev.flash.eyesworld.objConverter.OBJFileLoader;
import dev.flash.eyesworld.renderEngine.DisplayManager;
import dev.flash.eyesworld.renderEngine.Loader;
import dev.flash.eyesworld.renderEngine.MasterRenderer;
import dev.flash.eyesworld.terrains.Terrain;
import dev.flash.eyesworld.textures.TerrainTexture;
import dev.flash.eyesworld.textures.TerrainTexturePack;
import dev.flash.eyesworld.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
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
		
		//Terrain Texture Stuff
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
		
		//Dragon
		ModelData dragonData = OBJFileLoader.loadOBJ("dragon");
		RawModel dragonModel = loader.loadToVAO(
				dragonData.getVertices(), dragonData.getTextureCoords(), dragonData.getNormals(), dragonData.getIndices());
		TexturedModel staticDragonModel = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("white")));
		ModelTexture dragonTexture = staticDragonModel.getTexture();
		dragonTexture.setShineDamper(10);
		dragonTexture.setReflectivity(1);
		Entity dragonEntity = new Entity(staticDragonModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);
		
		
		//Player
		Player player = new Player(staticDragonModel, new Vector3f(100, 0, -5), 0, 270, 0, 1);
		
		
		//Lamps
		ModelData lampData = OBJFileLoader.loadOBJ("lamp");
		RawModel lampModel = loader.loadToVAO(
				lampData.getVertices(), lampData.getTextureCoords(), lampData.getNormals(), lampData.getIndices());
		TexturedModel staticLampModel = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
		//staticLampModel.getTexture().setTransparency(true);
		staticLampModel.getTexture().setFakeLighting(true);
		
		//Fern
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		
		RawModel fernModel = loader.loadToVAO(
				fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		
		TexturedModel staticFernModel = new TexturedModel(fernModel, fernTextureAtlas);
		staticFernModel.getTexture().setTransparency(true);
		//staticFernModel.getTexture().setFakeLighting(true);
		
		List<Entity> ferns = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 120; i++) {
			float x = random.nextFloat() * 800;
			float z = random.nextFloat() * -800;
			float y = terrain.getHeightOfTerrain(x, z);
			
			ferns.add(new Entity(staticFernModel, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 180, 0, 1));
		}
		
		
		//Grass
		ModelData treeData = OBJFileLoader.loadOBJ("lowPolyTree");
		RawModel treeModel = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
		TexturedModel staticTreeModel = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("lowPolyTree")));
		ModelTexture treeTexture = staticTreeModel.getTexture();
		
		treeTexture.setShineDamper(10);
		treeTexture.setReflectivity(0.25f);
		//staticGrassModel.getTexture().setTransparency(true);
		//staticGrassModel.getTexture().setFakeLighting(true);
		List<Entity> trees = new ArrayList<Entity>();
		//Random random = new Random();
		for (int i = 0; i < 90; i++) {
			float x = random.nextFloat() * 800;
			float z = random.nextFloat() * -800;
			float y = terrain.getHeightOfTerrain(x, z);
			
			trees.add(new Entity(staticTreeModel, new Vector3f(x, y, z), 0, random.nextFloat() * 180, 0, 1));
		}
		
		Light sun = new Light(new Vector3f(0, 500, -7000), new Vector3f(0.4f, 0.4f, 0.4f));
		List<Light> lights = new ArrayList<Light>();
		lights.add(sun);
		lights.add(new Light(new Vector3f(185, terrain.getHeightOfTerrain(185, -293)+20, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(370, terrain.getHeightOfTerrain(370, -300)+20, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(293, terrain.getHeightOfTerrain(293, -305)+20, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));
		
		List<Entity> lamps = new ArrayList<Entity>();
		
		lamps.add(new Entity(staticLampModel, new Vector3f(185, terrain.getHeightOfTerrain(185, -293), -293), 0, 0, 0, 1));
		lamps.add(new Entity(staticLampModel, new Vector3f(370, terrain.getHeightOfTerrain(370, -300), -300), 0, 0, 0, 1));
		lamps.add(new Entity(staticLampModel, new Vector3f(293, terrain.getHeightOfTerrain(293, -305), -305), 0, 0, 0, 1));
		
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("Flash_Silver_Squared"), new Vector2f(0.9f, -0.9f), new Vector2f(0.1f, 0.1f));
		guis.add(gui);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		float x = 0;
		while (!Display.isCloseRequested()) {
			x += 0.1f;
			dragonEntity.increasePosition(0, (float) (Math.sin(x)), 0);
			dragonEntity.increaseRotation(0, 0.15f, 0);
			
			player.move(terrain);
			camera.move();
			renderer.processEntity(player);
			renderer.processEntity(dragonEntity);
			for (Entity tree : trees)
				renderer.processEntity(tree);
			for (Entity fern : ferns)
				renderer.processEntity(fern);
			for (Entity lamp : lamps)
				renderer.processEntity(lamp);
			renderer.processTerrain(terrain);
			
			renderer.render(lights, camera);
			
			guiRenderer.render(guis);
			
			DisplayManager.updateDisplay();
		}
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
