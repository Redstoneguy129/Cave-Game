package me.cameronwhyte.cavegame.engine;

import me.cameronwhyte.cavegame.engine.graph.Render;
import me.cameronwhyte.cavegame.engine.scene.Scene;

public class Engine {
    public static final int TARGET_UPS = 20;
    private final IProgramLogic programLogic;
    private final Window window;
    private Render render;
    private boolean running;
    private Scene scene;
    private int targetFps;
    private int targetUps;

    public Engine(String windowTitle, Window.WindowOptions options, IProgramLogic programLogic) {
        window = new Window(windowTitle, options, () -> {
            resize();
            return null;
        });
        this.targetFps = options.fps;
        this.targetUps = options.ups;
        this.programLogic = programLogic;
        this.render = new Render();
        this.scene = new Scene();
        this.programLogic.init(window, scene, render);
        this.running = true;
    }

    private void CleanUp() {
        render.CleanUp();
        scene.CleanUp();
        window.CleanUp();
    }

    private void resize() {
    }

    private void run() {
        long initialTime = System.currentTimeMillis();
        float timeU = 1000.0f / targetUps;
        float timeR = targetFps > 0 ? 1000.0f / targetFps : 0;
        float deltaUpdate = 0;
        float deltaFps = 0;

        long updateTime = initialTime;
        while (running && !window.windowShouldClose()) {
            window.pollEvents();

            long now = System.currentTimeMillis();
            deltaUpdate += (now - initialTime) / timeU;
            deltaFps += (now - initialTime) / timeR;

            if (targetFps <= 0 || deltaFps >= 1) {
                this.programLogic.input(window, scene, now - initialTime);
            }

            if (deltaUpdate >= 1) {
                long diffTimeMillis = now - updateTime;
                this.programLogic.update(window, scene, diffTimeMillis);
                updateTime = now;
                deltaUpdate--;
            }

            if (targetFps <= 0 || deltaFps >= 1) {
                render.render(window, scene);
                deltaFps--;
                window.update();
            }
            initialTime = now;
        }

        CleanUp();
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }
}
