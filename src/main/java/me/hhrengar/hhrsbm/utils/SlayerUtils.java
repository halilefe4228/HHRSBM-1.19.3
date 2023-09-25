package me.hhrengar.hhrsbm.utils;

import me.hhrengar.hhrsbm.ConfigHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlayerUtils {
    public static boolean saySlayerTimeChat = true;
    public static long slayerStartDate = 0;
    public static long bossSpawnDate = 0;
    public static Boolean bossAlive = false;
    public static final Logger LOGGER = LoggerFactory.getLogger("hhrsbm");
    public static void onSlayerKill(){
        long currentTime = MinecraftClient.getInstance().world.getTime();
        if(slayerStartDate==0) return;
        if(!ConfigHandler.config.get("onSlayerKill")) return;
        MinecraftClient.getInstance().player.sendMessage(Text.of("§6 [SLAYER] §f Boss took §6"+String.valueOf(((bossSpawnDate-slayerStartDate)/20.0))+"s §fto spawn.\n §6[SLAYER] §f Boss took §6"+String.valueOf(((currentTime-bossSpawnDate)/20.0))+"s §fto kill. \n §6[SLAYER] §f Boss took §6"+String.valueOf(((currentTime-slayerStartDate)/20.0))+"s §fto spawn and kill."));
        bossAlive = false;
    }
    public static void onBossSpawn(){
        if(bossAlive||slayerStartDate==0) return;
        bossSpawnDate = MinecraftClient.getInstance().world.getTime();
        bossAlive = true;
    }
    public static void onSlayerStart(){
        slayerStartDate= MinecraftClient.getInstance().world.getTime();
    }
}
