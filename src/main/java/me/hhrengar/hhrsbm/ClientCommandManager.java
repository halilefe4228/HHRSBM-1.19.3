package me.hhrengar.hhrsbm;

import me.hhrengar.hhrsbm.gui.ConfigGUI;
import me.hhrengar.hhrsbm.gui.ConfigScreen;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.function.Consumer;

public class ClientCommandManager {
    public static HashMap<String,String> aliasCommands = new HashMap<String,String>();


    public static void registerCommands(){
        assignAliasCommands();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {dispatcher.register(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("hhrsbm").executes(context -> {
            new Thread()
            {
                @Override
                public void run()
                {
                    try {Thread.sleep(200);} catch (InterruptedException e) {throw new RuntimeException(e);}
                    ConfigGUI.forceOpenScreen=true;
                }
            }.start();
            return 1;
        }));});
        aliasCommands.forEach((key,value)->{
            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {dispatcher.register(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal(key).executes(context -> {
                MinecraftClient.getInstance().player.networkHandler.sendCommand(value);
                return 1;
            }));});
        });
    }
    public static void assignAliasCommands(){
        aliasCommands.clear();
        AliasHandler.aliases.forEach((key,value)->{aliasCommands.put(key,value);});
    }
}
