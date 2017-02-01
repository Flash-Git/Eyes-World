package dev.flash.eyesworld.utils;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.terrains.Terrain;
import dev.flash.eyesworld.terrains.TerrainManager;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Flash on 04/01/2017.
 */

public class EntitySelector extends MousePicker {
	
	private static final int RECURSION_COUNT = 250;
	private static final float RAY_RANGE = 2000;
	
	private Vector3f currentTerrainPoint;
	
	private TerrainManager terrainManager;
	
	public EntitySelector(Camera camera, Matrix4f projectionMatrix, TerrainManager terrainManager) {
		super(camera, projectionMatrix);
		this.terrainManager = terrainManager;
	}
	
	@Override
	public void update() {
		super.update();
		if (intersectionInRange(0, RAY_RANGE, currentRay)) {
			currentTerrainPoint = binarySearch(0, 0, RAY_RANGE, currentRay);
		} else {
			currentTerrainPoint = null;
		}
	}
	
	//
	
	private Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f camPos = camera.getPosition();
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}
	
	private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {//TODO not perfect, can miss the point if it collides W/ multiple terrain surfaces
		float half = start + ((finish - start) / 2f);
		if (count >= RECURSION_COUNT) {
			Vector3f endPoint = getPointOnRay(ray, half);
			Terrain terrain = terrainManager.getTerrain(endPoint.getX(), endPoint.getZ());
			if (terrain != null) {
				return endPoint;
			} else {
				return null;
			}
		}
		if (intersectionInRange(start, half, ray)) {
			return binarySearch(count + 1, start, half, ray);
		} else {
			return binarySearch(count + 1, half, finish, ray);
		}
	}
	
	private boolean intersectionInRange(float start, float finish, Vector3f ray) {
		Vector3f startPoint = getPointOnRay(ray, start);
		Vector3f endPoint = getPointOnRay(ray, finish);
		return !isUnderGround(startPoint) && isUnderGround(endPoint);
	}
	
	private boolean isUnderGround(Vector3f testPoint) {
		Terrain terrain = terrainManager.getTerrain(testPoint.getX(), testPoint.getZ());
		float height = 0;
		if (terrain != null) {
			height = terrain.getHeightOfTerrain(testPoint.getX(), testPoint.getZ());
		}
		return testPoint.y < height;
	}
	
	
	public Vector3f getCurrentTerrainPoint() {
		return currentTerrainPoint;
	}
	
}
