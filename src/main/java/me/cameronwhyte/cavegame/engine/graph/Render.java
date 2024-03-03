package me.cameronwhyte.cavegame.engine.graph;

import me.cameronwhyte.cavegame.engine.Window;
import me.cameronwhyte.cavegame.engine.scene.Scene;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    public Render() {
        GL.createCapabilities();
    }

    public void CleanUp() {}

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
