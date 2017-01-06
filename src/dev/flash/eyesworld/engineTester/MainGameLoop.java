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
import dev.flash.eyesworld.textures.ModelTexture;
import dev.flash.eyesworld.textures.TerrainTexture;
import dev.flash.eyesworld.textures.TerrainTexturePack;
import dev.flash.eyesworld.utils.EntitySelector;
import dev.flash.eyesworld.water.WaterFrameBuffers;
import dev.flash.eyesworld.water.WaterRenderer;
import dev.flash.eyesworld.water.WaterShader;
import dev.flash.eyesworld.water.WaterTile;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

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
		Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightMap");
		Terrain terrain3 = new Terrain(0, 0, loader, texturePack, blendMap, "heightMap");
		Terrain terrain4 = new Terrain(-1, 0, loader, texturePack, blendMap, "heightMap");
		List<Terrain> terrains = new ArrayList<>();
		terrains.add(terrain);
		terrains.add(terrain2);
		terrains.add(terrain3);
		terrains.add(terrain4);
		
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
		
		List<Entity> trees = new ArrayList<Entity>();
		for (int i = 0; i < 160; i++) {
			float x = random.nextFloat() * 800;
			float z = random.nextFloat() * -800;
			float y = terrain.getHeightOfTerrain(x, z);
			
			trees.add(new Entity(staticTreeModel, new Vector3f(x, y, z), 0, random.nextFloat() * 180, 0, 1));
		}
		
		Light sun = new Light(new Vector3f(0, 5000, -2000), new Vector3f(0.9f, 0.9f, 0.9f));
		List<Light> lights = new ArrayList<Light>();
		lights.add(sun);
		lights.add(new Light(new Vector3f(185, terrain.getHeightOfTerrain(185, -293) + 15, -293),
				new Vector3f(2, 0, 0), new Vector3f(1, 0.0007f, 0.00015f)));
		lights.add(new Light(new Vector3f(370, terrain.getHeightOfTerrain(370, -300) + 15, -300),
				new Vector3f(0, 2, 2), new Vector3f(1, 0.0007f, 0.00015f)));
		lights.add(new Light(new Vector3f(293, terrain.getHeightOfTerrain(293, -305) + 15, -305),
				new Vector3f(2, 2, 0), new Vector3f(1, 0.0007f, 0.00015f)));
		
		List<Entity> lamps = new ArrayList<Entity>();
		
		lamps.add(new Entity(staticLampModel, new Vector3f(185, terrain.getHeightOfTerrain(185, -293), -293)
				, 0, 0, 0, 1));
		lamps.add(new Entity(staticLampModel, new Vector3f(370, terrain.getHeightOfTerrain(370, -300), -300)
				, 0, 0, 0, 1));
		lamps.add(new Entity(staticLampModel, new Vector3f(293, terrain.getHeightOfTerrain(293, -305), -305)
				, 0, 0, 0, 1));
		
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture flashIcon = new GuiTexture(loader.loadTexture("Flash_Silver_Squared"), new Vector2f(0.9f, -0.9f), new Vector2f(0.1f, 0.1f));
		guis.add(flashIcon);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		EntitySelector picker = new EntitySelector(camera, renderer.getProjectionMatrix(), terrain);
		
		List<Entity> entities = new ArrayList<Entity>();
		entities.addAll(trees);
		entities.addAll(lamps);
		entities.addAll(ferns);
		entities.add(player);
		entities.add(dragonEntity);
		
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		
		
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<>();
		waters.add(new WaterTile(220, -170, -0.5f));
		
		
		//GuiTexture reflection = new GuiTexture(buffers.getReflectionTexture(), new Vector2f(-0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		//GuiTexture refraction = new GuiTexture(buffers.getRefractionTexture(), new Vector2f(-0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		
		
		//float x = 0;
		while (!Display.isCloseRequested()) {
			float fps = 1000/DisplayManager.getFrameTimeMillis();
			String title = Float.toString(fps);
			Display.setTitle(title);
			//x += 0.065f;
			//dragonEntity.increasePosition(0, (float) (Math.sin(x)), 0);
			//dragonEntity.increaseRotation(0, 0.15f, 0);
			player.move(terrain);
			camera.move();
			//picker.update();
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);//allows clipping/culling on one side of a plane TODO verify 2:30 3rd water opengl thinmatrix
			
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - waters.get(0).getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			
			renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, 1, 0, -waters.get(0).getHeight()+0.2f));//little offset reduces edge water glitch
			
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, -1, 0, waters.get(0).getHeight()+0.2f));//little offset reduces edge water glitch
			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);//Some drivers ignore this command
			buffers.unbindCurrentFrameBuffer();
			
			renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));//hacky and gross cos drivers ignore command
			waterRenderer.render(waters, camera, sun);
			guiRenderer.render(guis);
			
			
			Vector3f terrainPoint = picker.getCurrentTerrainPoint();
			if (terrainPoint != null) {
				dragonEntity.setPosition(terrainPoint);
			}
			
			DisplayManager.updateDisplay();
		}
		
		buffers.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();//water renderer not need to clean up?
		waterShader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
