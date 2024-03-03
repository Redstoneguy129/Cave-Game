package me.cameronwhyte.cavegame.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import static org.lwjgl.glfw.GLFW.*;

public class CaveGameConfiguration {

    private int windowWidth = 960;
    private int windowHeight = 540;
    private KeyBindings keyBindings = new KeyBindings();

    private CaveGameConfiguration(int windowWidth, int windowHeight, KeyBindings keyBindings) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.keyBindings = keyBindings;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public KeyBindings getKeyBindings() {
        return keyBindings;
    }

    @Override
    public String toString() {
        return "windowWidth=" + windowWidth + "\n" +
                "windowHeight=" + windowHeight + "\n" +
                "KeyBindings=" + keyBindings;
    }

    public static CaveGameConfiguration read(Path path) throws IOException {
        URL defaultConfig = CaveGameConfiguration.class.getClassLoader().getResource("default-config.toml");
        if (defaultConfig == null) {
            throw new RuntimeException("Default configuration not found");
        }
        // Read the config from path
        try (final CommentedFileConfig config = CommentedFileConfig.builder(path)
                .defaultData(defaultConfig)
                .autosave()
                .preserveInsertionOrder()
                .sync()
                .build()) {
            config.load();
            final int windowWidth = config.getIntOrElse("windowWidth", 960);
            final int windowHeight = config.getIntOrElse("windowHeight", 540);
            final CommentedConfig keyBindings = config.getOrElse("KeyBindings", CommentedConfig.inMemory());
            return new CaveGameConfiguration(windowWidth, windowHeight, new KeyBindings(keyBindings));
        }
    }

    private static class KeyBindings {
        private int forward = GLFW_KEY_W;
        private int backward = GLFW_KEY_S;
        private int left = GLFW_KEY_A;
        private int right = GLFW_KEY_D;
        private int jump = GLFW_KEY_SPACE;
        private int crouch = GLFW_KEY_LEFT_SHIFT;
        private int sprint = GLFW_KEY_LEFT_CONTROL;
        private int interact = GLFW_MOUSE_BUTTON_RIGHT;
        private int attack = GLFW_MOUSE_BUTTON_LEFT;

        private KeyBindings() {}

        private KeyBindings(CommentedConfig config) {
            if(config != null) {
                this.forward = config.getIntOrElse("forward", GLFW_KEY_W);
                this.backward = config.getIntOrElse("backward", GLFW_KEY_S);
                this.left = config.getIntOrElse("left", GLFW_KEY_A);
                this.right = config.getIntOrElse("right", GLFW_KEY_D);
                this.jump = config.getIntOrElse("jump", GLFW_KEY_SPACE);
                this.crouch = config.getIntOrElse("crouch", GLFW_KEY_LEFT_SHIFT);
                this.sprint = config.getIntOrElse("sprint", GLFW_KEY_LEFT_CONTROL);
                this.interact = config.getIntOrElse("interact", GLFW_MOUSE_BUTTON_RIGHT);
                this.attack = config.getIntOrElse("attack", GLFW_MOUSE_BUTTON_LEFT);
            }
        }

        public int getForward() {
            return forward;
        }

        public int getBackward() {
            return backward;
        }

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        public int getJump() {
            return jump;
        }

        public int getCrouch() {
            return crouch;
        }

        public int getSprint() {
            return sprint;
        }

        public int getInteract() {
            return interact;
        }

        public int getAttack() {
            return attack;
        }

        @Override
        public String toString() {
            return "KeyBindings{"
                    + "forward=" + forward
                    + ", backward=" + backward
                    + ", left=" + left
                    + ", right=" + right
                    + ", jump=" + jump
                    + ", crouch=" + crouch
                    + ", sprint=" + sprint
                    + ", interact=" + interact
                    + ", attack=" + attack
                    + '}';
        }
    }
}
