package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.Utils.Utils;
import dev.flash.eyesworld.renderEngine.DisplayManager;
import dev.flash.eyesworld.renderEngine.Loader;
import dev.flash.eyesworld.renderEngine.RawModel;
import dev.flash.eyesworld.renderEngine.Renderer;
import org.lwjgl.opengl.Display;

/**
 * Created by Flash on 29/12/2016.
 */

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                //Left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                //Right top triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f,
        };

        RawModel model = loader.loadToVao(vertices);

        while(!Display.isCloseRequested()){
            renderer.prepare();
            //gamelogic
            //render
            renderer.render(model);

            DisplayManager.updateDisplay();
        }
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
