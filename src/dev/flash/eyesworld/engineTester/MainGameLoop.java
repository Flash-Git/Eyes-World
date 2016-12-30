package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.renderEngine.DisplayManager;
import dev.flash.eyesworld.renderEngine.Loader;
import dev.flash.eyesworld.renderEngine.RawModel;
import dev.flash.eyesworld.renderEngine.Renderer;
import dev.flash.eyesworld.shaders.StaticShader;
import org.lwjgl.opengl.Display;

/**
 * Created by Flash on 29/12/2016.
 */

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();


        float[] vertices = {
                -0.5f, 0.5f, 0f,    //V0
                -0.5f, -0.5f, 0f,   //V1
                0.5f, -0.5f, 0f,    //V2
                0.5f, 0.5f, 0f     //V3
        };

        int[] indices = {
                0, 1, 3,    //top left v0, v1, v3
                3, 1, 2     //bot right v3, v1, v2
        };

        RawModel model = loader.loadToVao(vertices, indices);

        while(!Display.isCloseRequested()){
            renderer.prepare();
            shader.start();
            //gamelogic
            //render
            renderer.render(model);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
