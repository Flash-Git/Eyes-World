package dev.flash.eyesworld.shaders;

/**
 * Created by Flash on 30/12/2016.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/dev/flash/eyesworld/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/dev/flash/eyesworld/shaders/fragmentShader.txt";

    public StaticShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes(){
        super.bindAttribute(0, "position");
    }

}
