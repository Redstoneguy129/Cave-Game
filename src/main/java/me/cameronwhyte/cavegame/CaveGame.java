package me.cameronwhyte.cavegame;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import me.cameronwhyte.cavegame.config.CaveGameConfiguration;
import me.cameronwhyte.cavegame.engine.Engine;
import me.cameronwhyte.cavegame.engine.IProgramLogic;
import me.cameronwhyte.cavegame.engine.Window;
import me.cameronwhyte.cavegame.engine.graph.Render;
import me.cameronwhyte.cavegame.engine.scene.Scene;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class CaveGame implements IProgramLogic {

    private final String gameTitle;

    private static CaveGameConfiguration configuration = null;

    private long window;
    public final IPCClient discordClient;

    private CaveGame(String gameTitle) throws IOException {
        this.gameTitle = gameTitle;
        configuration = CaveGameConfiguration.read(Path.of("config.toml"));
        this.discordClient = new IPCClient(1213807013650497546L);
    }

    public void launchPresence() throws NoDiscordClientException {
        RichPresence.Builder builder = new RichPresence.Builder();
        this.discordClient.setListener(new IPCListener() {
            @Override
            public void onReady(IPCClient client) {
                builder.setState("ooo State").setDetails("ooo Details").setLargeImage("cobblestone")
                        .setStartTimestamp(OffsetDateTime.now());
                client.sendRichPresence(builder.build());
            }
        });
        this.discordClient.connect();
    }

    public void start() {
        Engine engine = new Engine(this.gameTitle, new Window.WindowOptions(), this);
        engine.start();
    }

    public static CaveGameConfiguration getConfiguration() {
        return configuration;
    }

    public static void main(String[] args) throws IOException, NoDiscordClientException {
        CaveGame game = new CaveGame("Cave Game");
        game.launchPresence();
        game.start();
        game.discordClient.close();
    }

    @Override
    public void CleanUp() {

    }

    @Override
    public void init(Window window, Scene scene, Render render) {

    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {

    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {

    }
}