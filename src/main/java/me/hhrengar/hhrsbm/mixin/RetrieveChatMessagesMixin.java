package me.hhrengar.hhrsbm.mixin;

import com.google.common.collect.Lists;
import me.hhrengar.hhrsbm.ConfigHandler;
import me.hhrengar.hhrsbm.event.ChatMessageGetter;
import me.hhrengar.hhrsbm.gui.ConfigGUI;
import me.hhrengar.hhrsbm.gui.ConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(ChatHud.class)
public class RetrieveChatMessagesMixin {
    @Shadow @Final
    private final List<ChatHudLine> messages = Lists.newArrayList();
    @Shadow @Final
    private List<ChatHudLine.Visible> visibleMessages;
    private HashMap<String,Boolean> messagesToBeDeleted = new HashMap<String,Boolean>();
    private int upToConfig = 0;
    @Inject(
            method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
            at = @At("HEAD"))
    public void addMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        if(MinecraftClient.getInstance().inGameHud.getChatHud()!=null){
            ChatMessageGetter.messageList(signature,message);
            if(checkIfMessageToBeDeleted(ChatMessageGetter.extractAndJoinLiterals(message.toString()))) {
                LoggerFactory.getLogger("hhrsbm").info("Successfully removing the line: "+ChatMessageGetter.extractAndJoinLiterals(String.valueOf(this.messages.get(this.messages.size()-1))));
                MinecraftClient.getInstance().inGameHud.getChatHud().removeMessage(signature);

            }

        }
    }
    public Boolean checkIfMessageToBeDeleted(String text){
        if(ConfigHandler.upToDate!=upToConfig) updateChatChecks();
        List<String> keys = new ArrayList<String>(messagesToBeDeleted.keySet());
        for(int l = 0;l<keys.size();l++) {
            if (text.startsWith(keys.get(l)) && messagesToBeDeleted.get(keys.get(l))) {
                return true;
            }
        }
        return false;
    }
    public void updateChatChecks(){
        upToConfig= ConfigHandler.upToDate;
        messagesToBeDeleted.put("Your Implosion hit",ConfigHandler.config.get("hideImplosionText"));
        messagesToBeDeleted.put("There are blocks in the way!",ConfigHandler.config.get("hideBlockOnWayText"));
        messagesToBeDeleted.put("Your Mining Speed Boost has expired!",ConfigHandler.config.get("hideMSBExpired"));
        messagesToBeDeleted.put("This ability is on cooldown",ConfigHandler.config.get("hideAbilityOnCD"));
    }
}

