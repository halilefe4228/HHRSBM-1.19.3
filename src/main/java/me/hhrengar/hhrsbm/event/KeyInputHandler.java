package me.hhrengar.hhrsbm.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_HHR = "key.category.hhrsbm.keybinds";
    public static final String KEY_SAY_TEXT = "key.hhrsbm.saytext";

    public static KeyBinding sayTextKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(sayTextKey.wasPressed()){
                client.player.sendMessage(Text.of("§4TESTESTETS"));
            }
        });
    }

    public static void register(){
        sayTextKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SAY_TEXT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY_HHR
        ));
        registerKeyInputs();
    }
}
