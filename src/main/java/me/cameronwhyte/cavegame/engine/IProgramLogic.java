package me.cameronwhyte.cavegame.engine;

import me.cameronwhyte.cavegame.engine.graph.Render;
import me.cameronwhyte.cavegame.engine.scene.Scene;

public interface IProgramLogic {
    void CleanUp();
    void init(Window window, Scene scene, Render render);
    void input(Window window, Scene scene, long diffTimeMillis);
    void update(Window window, Scene scene, long diffTimeMillis);
}
