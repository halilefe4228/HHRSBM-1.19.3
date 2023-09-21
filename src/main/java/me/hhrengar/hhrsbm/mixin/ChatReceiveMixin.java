package me.hhrengar.hhrsbm.mixin;

import me.hhrengar.hhrsbm.ChatMessageFunctions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.logging.LogManager;

@Mixin(ChatHud.class)
public class ChatReceiveMixin {
    @Shadow @Final
    private List<ChatHudLine.Visible> visibleMessages;

    @Inject(
            method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
            at = @At("HEAD"))
    public void addMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        if(MinecraftClient.getInstance().inGameHud!=null){
            ChatMessageFunctions.oneAndOnly(message);
        }
    }
}

