package me.hhrengar.hhrsbm.event;

import me.hhrengar.hhrsbm.gui.ConfigGUI;
import me.hhrengar.hhrsbm.gui.ConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.*;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;

import java.io.Closeable;
import java.util.Map;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_HHR = "key.category.hhrsbm.keybinds";
    public static final String KEY_SAY_TEXT = "key.hhrsbm.saytext";

    public static KeyBinding sayTextKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(sayTextKey.wasPressed()){
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
