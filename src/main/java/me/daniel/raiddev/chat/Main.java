package me.daniel.raiddev.chat;
import me.daniel.raiddev.chat.config.WhiteListConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
public class Main implements ClientModInitializer {
    public static KeyBinding key;
    @Override
    public void onInitializeClient() {
        key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Словарь",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                "Daniel Mods - Chat Helper"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (key.wasPressed()) {
                var file = FabricLoader.getInstance().getGameDir().resolve("Daniel_Mods/Chat_Helper/white_list.txt").toFile();
                if (file.exists()) Util.getOperatingSystem().open(file);
            }
        });
        WhiteListConfig.load();
    }
}