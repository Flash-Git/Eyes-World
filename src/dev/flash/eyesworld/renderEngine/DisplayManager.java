package dev.flash.eyesworld.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

/**
 * Created by Flash on 28/12/2016.
 */

public class DisplayManager {
	
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	private static int FPS_CAP = 240;
	
	
	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay() {
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Eye's World v0");
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	
	
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime);
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeMillis() {
		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
}
