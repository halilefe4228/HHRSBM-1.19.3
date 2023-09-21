package me.hhrengar.hhrsbm;

import me.hhrengar.hhrsbm.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class HHRSBMClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();
    }
}
